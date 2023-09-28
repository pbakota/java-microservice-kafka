package rs.lab.payments.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import rs.lab.common.dto.OrderEvent;
import rs.lab.common.dto.PaymentEvent;
import rs.lab.payments.models.PaymentEntity;
import rs.lab.payments.models.PaymentsRepository;

@Slf4j
@Component
public class OrderEventConsumer {

    @Autowired
    private PaymentsRepository paymentsRepository;

    @Autowired
    private KafkaTemplate<String, PaymentEvent> paymentEventProducer;

    @Autowired
    private KafkaTemplate<String, OrderEvent> orderEventProducer;

    @KafkaListener(topics = "java-new-orders", groupId = "java-orders-group")
    public void consume(OrderEvent orderEvent) {
        log.info("Received: {}", orderEvent);

        var order = orderEvent.getOrder();

        var payment = PaymentEntity.builder()
                .orderId(order.getOrderId())
                .amount(order.getAmount())
                .mode(order.getPaymentMethod())
                .status("Success")
                .build();

        try {

            paymentsRepository.save(payment);

            var paymentEvent = PaymentEvent.builder()
                    .order(order)
                    .type("PAYMENT_CREATED")
                    .build();

            paymentEventProducer.send("java-new-payments", paymentEvent);
            log.info("{} -> Event sent: {}", "java-new-payments", paymentEvent);

        } catch (Exception ex) {
            log.error("Error when consume order event", ex);

            payment.setStatus("Failed");
            paymentsRepository.save(payment);

            var reversedOrderEvent = OrderEvent.builder()
                    .order(order)
                    .type("ORDER_REVERSED")
                    .build();

            orderEventProducer.send("java-reversed-orders", reversedOrderEvent);
            log.info("{} -> Event sent: {}", "java-reversed-orders", reversedOrderEvent);
        }
    }
}
