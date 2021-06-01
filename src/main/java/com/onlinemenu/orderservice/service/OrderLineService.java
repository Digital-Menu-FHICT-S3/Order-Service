package com.onlinemenu.orderservice.service;

import com.onlinemenu.orderservice.entity.OrderLine;
import com.onlinemenu.orderservice.repository.OrderLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderLineService {

    @Autowired
    private OrderLineRepository orderLineRepository;

    public List<OrderLine> saveOrderLines(List<OrderLine> orderLines){ return orderLineRepository.saveAll(orderLines);}

    public OrderLine saveOrderLine(OrderLine orderLine) {
        return orderLineRepository.save(orderLine);
    }

    public Optional<OrderLine> findOrderLineById(Long orderLineId) {
        return orderLineRepository.findById(orderLineId);
    }

    public List<OrderLine> findOrderLinesByOrderId(Long orderId) {
        return orderLineRepository.findByOrderId(orderId);
    }
}
