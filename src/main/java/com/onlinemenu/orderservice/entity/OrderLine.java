package com.onlinemenu.orderservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderLineId;
    private Long orderId;
    private Long dishId;
    private int amount;

    public OrderLine(long _orderId, long _dishId, int _amount) {
        orderId = _orderId;
        dishId = _dishId;
        amount = _amount;
    }
}
