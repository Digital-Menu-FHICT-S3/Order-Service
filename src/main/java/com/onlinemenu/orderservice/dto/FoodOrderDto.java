package com.onlinemenu.orderservice.dto;

import com.onlinemenu.orderservice.entity.FoodOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodOrderDto {

    private FoodOrder foodOrder;
    private List<OrderLineDto> orderLines;

}
