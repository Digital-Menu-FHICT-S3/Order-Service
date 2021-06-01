package com.onlinemenu.orderservice.controller;

import com.onlinemenu.orderservice.entity.OrderLine;
import com.onlinemenu.orderservice.service.OrderLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("orders/orderlines")
public class OrderLineController {

    @Autowired
    private OrderLineService orderLineService;

    @GetMapping("/fromorder/{id}")
    public List<OrderLine> findOrderById(@PathVariable("id") Long orderId){
        return orderLineService.findOrderLinesByOrderId(orderId);
    }
}
