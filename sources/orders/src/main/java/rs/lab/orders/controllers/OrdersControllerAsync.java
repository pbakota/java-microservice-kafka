package rs.lab.orders.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import rs.lab.common.dto.CustomerOrder;
import rs.lab.orders.dto.OrderDto;
import rs.lab.orders.dto.mapping.OrderMapper;
import rs.lab.orders.services.OrdersServiceAsync;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import static rs.lab.common.AppConstants.TASK_EXECUTOR_CONTROLLER;

@Slf4j
@RestController
@RequestMapping("/async")
public class OrdersControllerAsync {

    @Autowired
    private OrdersServiceAsync ordersService;

    @Autowired
    private OrderMapper mapper;

    @Async(TASK_EXECUTOR_CONTROLLER)
    @PostMapping("/orders")
    public CompletableFuture<ResponseEntity<OrderDto>> createOrderAsync(@RequestBody CustomerOrder customerOrder) {
        log.info("Executing createOrderAsync controller");
        return ordersService.createOrder(customerOrder).thenApply((order) -> {
            return ResponseEntity.ok(mapper.toOrderDto(order));
        });
    }

    @Async(TASK_EXECUTOR_CONTROLLER)
    @GetMapping(value = "/orders")
    public CompletableFuture<ResponseEntity<Collection<OrderDto>>> getOrdersAsync(@PageableDefault Pageable pageable) {
        log.info("Executing getOrdersAsync controller");
        return ordersService.getOrders(pageable).thenApply((orders) -> {
            return ResponseEntity.ok(mapper.toOrdersDto(orders));
        });
    }
}
