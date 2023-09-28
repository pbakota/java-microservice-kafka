package rs.lab.stock.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import rs.lab.common.dto.DeliveryEvent;
import rs.lab.common.dto.PaymentEvent;
import rs.lab.stock.models.StockRepository;

@Slf4j
@Component
public class PaymentEventConsumer {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private KafkaTemplate<String, DeliveryEvent> deliveryEventProducer;

    @Autowired
    private KafkaTemplate<String, PaymentEvent> paymentEventKafkaTemplate;

    @KafkaListener(topics = "java-new-payments", groupId = "java-payments-group")
    public void consume(PaymentEvent paymentEvent) {
        log.info("Received: {}", paymentEvent);

        var order = paymentEvent.getOrder();
        try {
            var result = stockRepository.findByItem(order.getItem());

            result.ifPresentOrElse(s -> {
                s.setQuantity(s.getQuantity() - order.getQuantity());
                stockRepository.save(s);
            }, () -> {
                log.warn("Stock does not exists, reverting the order {}", order);
                throw new RuntimeException("Stock not available");
            });

            var deliveryEvent = DeliveryEvent.builder()
                    .order(order)
                    .type("STOCK_UPDATED")
                    .build();

            deliveryEventProducer.send("java-new-stock", deliveryEvent);
            log.info("{} -> Event sent: {}", "java-new-stock", deliveryEvent);

        } catch (Exception ex) {

            log.error("Error when consume payment event", ex);

            var reversedPaymentEvent = PaymentEvent.builder()
                    .order(order)
                    .type("PAYMENT_REVERSED")
                    .build();

            paymentEventKafkaTemplate.send("java-reversed-payments", reversedPaymentEvent);
            log.info("{} -> Event sent: {}", "java-reversed-payments", reversedPaymentEvent);
        }
    }
}
