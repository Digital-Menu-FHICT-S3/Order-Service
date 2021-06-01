package com.onlinemenu.orderservice.controller;

import com.onlinemenu.orderservice.dto.FoodOrderDto;
import com.onlinemenu.orderservice.dto.OrderLineDto;
import com.onlinemenu.orderservice.entity.FoodOrder;
import com.onlinemenu.orderservice.entity.OrderLine;
import com.onlinemenu.orderservice.service.OrderLineService;
import com.onlinemenu.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderLineService orderLineService;

    @PostMapping("/create")
    public FoodOrder saveOrder(@RequestBody FoodOrderDto foodOrderDto){

        if (foodOrderDto.getOrderLines() == null || foodOrderDto.getOrderLines().size() == 0)
             return new FoodOrder();

        //Save FoodOrder
        FoodOrder foodOrder = orderService.saveOrder(foodOrderDto.getFoodOrder());

        //Save OrderLines
        List<OrderLine> orderLines = new ArrayList<>();
        for (OrderLineDto dto : foodOrderDto.getOrderLines()) {
            orderLines.add(new OrderLine(foodOrder.getOrderId(),dto.getDishId(),dto.getAmount()));
        }
        orderLineService.saveOrderLines(orderLines);
        
        return foodOrder;
    }

    @GetMapping("/{id}")
    public Optional<FoodOrder> findOrderById(@PathVariable("id") Long orderId){
        return orderService.findOrderById(orderId);
    }

    @GetMapping("/all")
    public List<FoodOrder> getAllFoodOrder() {
        return orderService.GetAllFoodOrder();
    }

    @PutMapping("/update/{id}")
    public FoodOrder UpdateOrderToDone(@PathVariable("id") Long OrderId, @RequestBody FoodOrder foodOrder) {
        return orderService.saveOrder(foodOrder);
    }
}
