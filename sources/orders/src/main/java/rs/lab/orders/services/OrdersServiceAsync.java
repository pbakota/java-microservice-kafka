package rs.lab.orders.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import rs.lab.common.dto.CustomerOrder;
import rs.lab.common.dto.OrderEvent;
import rs.lab.orders.models.OrderEntity;
import rs.lab.orders.models.OrdersRepository;

import java.util.concurrent.CompletableFuture;

import static rs.lab.common.AppConstants.TASK_EXECUTOR_SERVICE;

@Slf4j
@Service
public class OrdersServiceAsync {

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @Async(TASK_EXECUTOR_SERVICE)
    public CompletableFuture<OrderEntity> createOrder(CustomerOrder customerOrder) {
        var order = OrderEntity.builder()
                .item(customerOrder.getItem())
                .amount(customerOrder.getAmount())
                .quantity(customerOrder.getQuantity())
                .status("Created")
                .build();

        try {
            ordersRepository.save(order);

            customerOrder.setOrderId(order.getId());
            var orderEvent = OrderEvent.builder()
                    .order(customerOrder)
                    .type("ORDER_CREATED")
                    .build();

            kafkaTemplate.send("java-new-orders", orderEvent);
            log.info("{} -> Event sent: {}", "java-new-orders", orderEvent);

            return CompletableFuture.completedFuture(order);
        } catch (Exception ex) {
            log.error("Error when saving order", ex);

            throw ex;
        }
    }

    @Async(TASK_EXECUTOR_SERVICE)
    public CompletableFuture<Page<OrderEntity>> getOrders(Pageable pageable) {
        log.info("Reading orders");
        return CompletableFuture.completedFuture(ordersRepository.findAll(pageable));
    }
}
