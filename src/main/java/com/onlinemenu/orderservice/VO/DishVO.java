package com.onlinemenu.orderservice.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishVO {
    private Long dishId;
    private Long categoryId;
    private String name;
    private double price;
    private String description;
    private String imageUrl;
    private List<Ingredient> ingredients;
}
