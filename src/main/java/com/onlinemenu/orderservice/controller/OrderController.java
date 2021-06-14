package com.onlinemenu.orderservice.controller;

import com.onlinemenu.orderservice.VO.DishVO;
import com.onlinemenu.orderservice.VO.Ingredient;
import com.onlinemenu.orderservice.dto.FoodOrderDto;
import com.onlinemenu.orderservice.dto.OrderLineDto;
import com.onlinemenu.orderservice.entity.FoodOrder;
import com.onlinemenu.orderservice.entity.OrderLine;
import com.onlinemenu.orderservice.service.OrderLineService;
import com.onlinemenu.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderLineService orderLineService;

    @Autowired
    private RestTemplate restTemplate;


    //TODO: Integration Test
    @PostMapping("/create")
    public FoodOrder saveOrder(@RequestBody FoodOrderDto foodOrderDto) {

        if (foodOrderDto.getOrderLines() == null || foodOrderDto.getOrderLines().size() == 0)
            return new FoodOrder();

        DishVO[] dishesWithIngredients = getIngredients(foodOrderDto);

        // Save Order
        FoodOrder foodOrder = orderService.saveOrder(foodOrderDto.getFoodOrder());
        var orderLines = foodOrderDto.getOrderLines().stream().map(
                f -> new OrderLine(foodOrder.getOrderId(), f.getDishId(), f.getAmount())).collect(Collectors.toList());
        orderLineService.saveOrderLines(orderLines);

        ArrayList<Ingredient> usedIngredients = getIngredients(foodOrderDto, dishesWithIngredients);

//        //Call SubtractIngredients from Ingredient Service.
//        String subtractIngredientsUrl = "http://locahost:9191/ingredient/subtract";
//        HttpEntity<List<Ingredient>> subtractRequest = new HttpEntity<>(usedIngredients);
//        restTemplate.postForObject(subtractIngredientsUrl, subtractRequest, Void.class);

        return foodOrder;
    }

    @GetMapping("/{id}")
    public Optional<FoodOrder> findOrderById(@PathVariable("id") Long orderId) {
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


    //Call GetAllIngredients from Menu Service To Get Ingredients of Dishes
    private DishVO[] getIngredients(FoodOrderDto foodOrderDto){
        String allIngredientUrl = "http://localhost:9191/menu/dishes/dishes-with-ingredients";
        List<Long> DishIds = foodOrderDto.getOrderLines()
                .stream().map(orderLine -> orderLine.getDishId())
                .collect(Collectors.toList());
        HttpEntity<List<Long>> ingredientRequest = new HttpEntity<>(DishIds);
        try {
            var dishesWithIngredients = restTemplate.postForEntity(allIngredientUrl, ingredientRequest, DishVO[].class);

        } catch (Exception e) {
            e.getCause();
        }

        return new DishVO[0];
    }

    private ArrayList<Ingredient> getIngredients(FoodOrderDto dto, DishVO[] dishes) {
        var usedIngredients = new ArrayList<Ingredient>();

        //Loop over all items in an order
        for (var orderLine : dto.getOrderLines()) {
            var dishId = orderLine.getDishId();
            var dishIngredients = Arrays.stream(Objects.requireNonNull(dishes))
                    .filter(x -> x.getDishId().equals(dishId))
                    .findFirst().get().getIngredients();

            //Loop over all ingredients in a dish
            for (var ingredient : dishIngredients) {
                var usedIngredient = new Ingredient(ingredient.getIngredientId(),
                        ingredient.getAmount() * orderLine.getAmount(), ingredient.getName());
                usedIngredients.add(usedIngredient);
            }
        }
        return usedIngredients;
    }
}
