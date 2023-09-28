package rs.lab.delivery.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import rs.lab.common.dto.DeliveryEvent;
import rs.lab.common.utils.StringUtils;
import rs.lab.delivery.models.DeliveryEntity;
import rs.lab.delivery.models.DeliveryRepository;

@Slf4j
@Component
public class DeliveryEventConsumer {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private KafkaTemplate<String, DeliveryEvent> deliveryEventKafkaTemplate;

    @KafkaListener(topics = "java-new-stock", groupId = "java-stock-group")
    public void consume(DeliveryEvent deliveryEvent) {
        log.info("Received: {}", deliveryEvent);

        var order = deliveryEvent.getOrder();
        var delivery = DeliveryEntity.builder()
                .address(order.getAddress())
                .orderId(order.getOrderId())
                .status("Success")
                .build();

        try {
            if (StringUtils.isEmpty(order.getAddress())) {
                throw new RuntimeException("Address not present");
            }

            deliveryRepository.save(delivery);
            log.info("Delivered to {}", delivery);

        } catch (Exception ex) {
            log.error("Error occurred in delivery");

            delivery.setStatus("Failed");
            deliveryRepository.save(delivery);

            var reverseDeliveryEvent = DeliveryEvent.builder()
                    .order(order)
                    .type("STOCK_REVERSED")
                    .build();

            deliveryEventKafkaTemplate.send("java-reversed-stock", reverseDeliveryEvent);
            log.info("{} -> Event sent: {}", "java-reversed-stock", reverseDeliveryEvent);
        }
    }
}
