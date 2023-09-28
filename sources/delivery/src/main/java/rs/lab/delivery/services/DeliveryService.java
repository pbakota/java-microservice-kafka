package rs.lab.delivery.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.lab.delivery.models.DeliveryEntity;
import rs.lab.delivery.models.DeliveryRepository;

@Slf4j
@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    public Page<DeliveryEntity> getDeliveries(Pageable pageable) {
        var deliveries = deliveryRepository.findAll(pageable);
        return deliveries;
    }
}
