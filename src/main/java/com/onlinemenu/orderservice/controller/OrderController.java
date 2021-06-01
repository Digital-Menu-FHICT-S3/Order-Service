package com.onlinemenu.orderservice.controller;

import com.onlinemenu.orderservice.entity.FoodOrder;
import com.onlinemenu.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public FoodOrder saveOrder(@RequestBody FoodOrder foodOrder){
        return orderService.saveOrder(foodOrder);
    }

    @GetMapping("/{id}")
    public Optional<FoodOrder> findOrderById(@PathVariable("id") Long orderId){
        return orderService.findOrderById(orderId);
    }

    @GetMapping("/all")
    public List<FoodOrder> getAllFoodOrder() {return orderService.GetAllFoodOrder();}

    @PutMapping("/update/{id}")
    public FoodOrder UpdateOrderToDone(@PathVariable("id") Long OrderId, @RequestBody FoodOrder foodOrder) {
        return orderService.saveOrder(foodOrder);
    }
}
