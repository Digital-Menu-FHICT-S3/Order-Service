package com.onlinemenu.orderservice.VO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {
    private Long ingredientId;
    private int amount;
    private String name;
}

