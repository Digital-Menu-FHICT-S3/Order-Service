package com.onlinemenu.orderservice.controller;

import com.onlinemenu.orderservice.VO.Dish;
import com.onlinemenu.orderservice.dto.FoodOrderDto;
import com.onlinemenu.orderservice.dto.OrderLineDto;
import com.onlinemenu.orderservice.entity.FoodOrder;
import com.onlinemenu.orderservice.entity.OrderLine;
import com.onlinemenu.orderservice.service.OrderLineService;
import com.onlinemenu.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/create")
    public FoodOrder saveOrder(@RequestBody FoodOrderDto foodOrderDto){

        if (foodOrderDto.getOrderLines() == null || foodOrderDto.getOrderLines().size() == 0)
             return new FoodOrder();

        //Call GetAllIngredients from Menu Service To Get Ingredients of Dishes
        String allIngredientUrl = "http://locahost:9191/menu/dishes/dishes-with-ingredients";
        Dish[] dishesWithIngredients = restTemplate.getForEntity(allIngredientUrl, Dish[].class).getBody();

        //TODO: Check if there's enough stock here and return error if not

        //Save FoodOrder
        FoodOrder foodOrder = orderService.saveOrder(foodOrderDto.getFoodOrder());

        //Save OrderLines
        List<OrderLine> orderLines = new ArrayList<>();
        for (OrderLineDto dto : foodOrderDto.getOrderLines()) {
            orderLines.add(new OrderLine(foodOrder.getOrderId(),dto.getDishId(),dto.getAmount()));
        }
        orderLineService.saveOrderLines(orderLines);

        //TODO Subtract ingredients from stock
        // Have List<Dish> (which includes ingredients)

        //Call SubtractIngredients from Menu Service.
        String subtractIngredientsUrl = "http://locahost:9191/menu/dishes/dishes-with-ingredients";
       restTemplate.postForEntity(allIngredientUrl, Dish[].class).getBody();



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
