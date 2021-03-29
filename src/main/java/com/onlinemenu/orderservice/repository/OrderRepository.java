package com.onlinemenu.orderservice.repository;

import com.onlinemenu.orderservice.entity.FoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface OrderRepository extends JpaRepository<FoodOrder, Long> {

}
