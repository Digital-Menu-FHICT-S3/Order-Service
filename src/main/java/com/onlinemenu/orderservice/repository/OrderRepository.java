package com.onlinemenu.orderservice.repository;

import com.onlinemenu.orderservice.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface OrderRepository extends JpaRepository<Order, Long> {

}
