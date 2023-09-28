package rs.lab.payments.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import rs.lab.common.dto.OrderEvent;
import rs.lab.common.dto.PaymentEvent;
import rs.lab.payments.models.PaymentsRepository;

@Slf4j
@Component
public class ReversePaymentConsumer {

    @Autowired
    private PaymentsRepository paymentsRepository;

    @Autowired
    private KafkaTemplate<String, OrderEvent> orderEventProducer;

    @KafkaListener(topics = "java-reversed-payments", groupId = "java-orders-group")
    public void consume(PaymentEvent paymentEvent) {
        log.info("Received: {}", paymentEvent);

        var order = paymentEvent.getOrder();
        try {

            var payments = paymentsRepository.findAllByOrderId(order.getOrderId());
            payments.forEach(p -> {
                p.setStatus("Failed");
                paymentsRepository.save(p);
            });

            var orderEvent = OrderEvent.builder()
                    .order(order)
                    .type("ORDER_REVERSED")
                    .build();

            orderEventProducer.send("java-reversed-orders", orderEvent);
            log.info("{} -> Event sent: {}", "java-reversed-orders", orderEvent);

        } catch (Exception ex) {
            log.error("Exception occurred while reverting payment {}", paymentEvent);
        }
    }
}
