package rs.lab.orders.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import rs.lab.common.dto.OrderEvent;
import rs.lab.orders.models.OrdersRepository;

@Slf4j
@Component
public class ReverseOrderConsumer {

    @Autowired
    private OrdersRepository ordersRepository;

    @KafkaListener(topics = "java-reversed-orders", groupId = "java-orders-group")
    public void consume(OrderEvent orderEvent) {
        log.info("Reverse order event: {}", orderEvent);

        try {
            var order = ordersRepository.findById(orderEvent.getOrder().getOrderId());
            order.ifPresent(o -> {
                o.setStatus("Failed");
                ordersRepository.save(o);
            });
            log.info("Order reversed {}", orderEvent.getOrder());
        } catch (Exception ex) {
            log.error("Exception occurred while reverting order {}", orderEvent);
        }
    }
}
