package rs.lab.delivery.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.lab.delivery.dto.DeliveryDto;
import rs.lab.delivery.dto.mappers.DeliveryMapper;
import rs.lab.delivery.services.DeliveryService;

import java.util.Collection;

@RestController
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private DeliveryMapper mapper;

    @GetMapping("/deliveries")
    public ResponseEntity<Collection<DeliveryDto>> getDeliveries(@PageableDefault Pageable pageable) {
        var deliveries = deliveryService.getDeliveries(pageable);
        return ResponseEntity.ok(mapper.toDeliveriesDto(deliveries));
    }
}
