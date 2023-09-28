package rs.lab.orders.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rs.lab.common.dto.CustomerOrder;
import rs.lab.orders.dto.OrderDto;
import rs.lab.orders.dto.mapping.OrderMapper;
import rs.lab.orders.services.OrdersService;

import java.util.Collection;

@RestController("/")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private OrderMapper mapper;

    @PostMapping("/orders")
    public ResponseEntity<OrderDto> createOrder(@RequestBody CustomerOrder customerOrder) {
        var order = ordersService.createOrder(customerOrder);
        return ResponseEntity.ok(mapper.toOrderDto(order));
    }

    @GetMapping(value = "/orders")
    public ResponseEntity<Collection<OrderDto>> getOrders(@PageableDefault Pageable pageable) {
        var orders = ordersService.getOrders(pageable);
        return ResponseEntity.ok(mapper.toOrdersDto(orders));
    }
}
