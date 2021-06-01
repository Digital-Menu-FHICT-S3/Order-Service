package com.onlinemenu.orderservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinemenu.orderservice.dto.FoodOrderDto;
import com.onlinemenu.orderservice.dto.OrderLineDto;
import com.onlinemenu.orderservice.entity.FoodOrder;
import com.onlinemenu.orderservice.entity.OrderStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    @Test
    void saveOrder() throws Exception {

        FoodOrder foodOrder = new FoodOrder(1l,2l, OrderStatus.ToDo,13.0,3.0,LocalDateTime.now());
        List<OrderLineDto> orderLines = new ArrayList<>();
        orderLines.add(new OrderLineDto(4l,5));
        orderLines.add(new OrderLineDto(1l,7));
        orderLines.add(new OrderLineDto(2l,2));

        FoodOrderDto OrderToPost = new FoodOrderDto(foodOrder,orderLines);
        String OrderAsString = mapper.writeValueAsString(OrderToPost);

        mvc.perform(post("/orders/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(OrderAsString)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(1L))
                .andExpect(jsonPath("$.tableId").value(2L))
                //.andExpect(jsonPath("$.OrderStatus").value(0))
                .andExpect(jsonPath("$.totalPrice").value(13.0))
                .andExpect(jsonPath("$.tip").value(3.0));

    }
}