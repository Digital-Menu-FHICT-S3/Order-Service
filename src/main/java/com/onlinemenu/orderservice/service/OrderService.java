package com.onlinemenu.orderservice.service;

import com.onlinemenu.orderservice.entity.FoodOrder;
import com.onlinemenu.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public FoodOrder saveOrder(FoodOrder foodOrder) {
        return orderRepository.save(foodOrder);
    }

    public Optional<FoodOrder> findOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    public List<FoodOrder> GetAllFoodOrder() { return orderRepository.findAll(); }
}

