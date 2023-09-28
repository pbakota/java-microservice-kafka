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
public class ReverseStockConsumer {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private KafkaTemplate<String, PaymentEvent> paymentEventKafkaTemplate;

    @KafkaListener(topics = "java-reversed-stock", groupId = "java-stock-group")
    public void consume(DeliveryEvent deliveryEvent) {
        log.info("Received: {}", deliveryEvent);

        var order = deliveryEvent.getOrder();
        try {

            var old = stockRepository.findByItem(order.getItem());
            old.ifPresent(s -> {
                s.setQuantity(s.getQuantity() + order.getQuantity());
                stockRepository.save(s);
            });

            var paymentEvent = PaymentEvent.builder()
                    .order(order)
                    .type("PAYMENT_REVERSED")
                    .build();

            paymentEventKafkaTemplate.send("java-reversed-payments", paymentEvent);
            log.info("{} -> Event sent: {}", "java-reversed-payments", paymentEvent);

        } catch (Exception ex) {
            log.error("Error when reversing stock {}", deliveryEvent);
        }
    }
}
