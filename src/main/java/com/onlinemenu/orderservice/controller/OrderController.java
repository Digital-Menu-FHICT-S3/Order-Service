package com.onlinemenu.orderservice.controller;

import com.onlinemenu.orderservice.entity.Order;
import com.onlinemenu.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public Order saveOrder(@RequestBody Order order){
        return orderService.saveOrder(order);
    }

    @GetMapping("/{id}")
    public Optional<Order> findOrderById(@PathVariable("id") Long orderId){
        return orderService.findOrderById(orderId);
    }

}
