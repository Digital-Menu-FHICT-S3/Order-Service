package com.onlinemenu.orderservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinemenu.orderservice.dto.FoodOrderDto;
import com.onlinemenu.orderservice.dto.OrderLineDto;
import com.onlinemenu.orderservice.entity.FoodOrder;
import com.onlinemenu.orderservice.entity.OrderStatus;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class OrderControllerTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    @Test
    @Disabled //Curently disabled because this test communicates with other services
    void saveOrder() throws Exception {

        FoodOrder foodOrder = new FoodOrder(1L,2L, OrderStatus.ToDo,13.0,3.0,LocalDateTime.now());
        List<OrderLineDto> orderLines = new ArrayList<>();
        orderLines.add(new OrderLineDto(4L,5));
        orderLines.add(new OrderLineDto(1L,7));
        orderLines.add(new OrderLineDto(2L,2));

        FoodOrderDto OrderToPost = new FoodOrderDto(foodOrder,orderLines);
        String OrderAsString = mapper.writeValueAsString(OrderToPost);

        mvc.perform(post("/orders/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(OrderAsString)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(1L))
                .andExpect(jsonPath("$.tableId").value(2L))
                .andExpect(jsonPath("$.orderStatus").value("ToDo"))
                .andExpect(jsonPath("$.totalPrice").value(13.0))
                .andExpect(jsonPath("$.tip").value(3.0));
                //.andExpect(jsonPath("$.orderLines[0].dishId").value(4l)); // orderlines not in response
    }

    @Test
    void shouldGetAllOrders() throws Exception {
        mvc.perform(get("/orders/all")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].orderId").value(1L))
                .andExpect(jsonPath("$.[0].tableId").value(1L))
                .andExpect(jsonPath("$.[0].totalPrice").value(13.0))
                .andExpect(jsonPath("$.[0].tip").value(3.0))
                .andExpect(jsonPath("$.[0].dateTime").value("2021-06-01T10:29:06.582"))
                .andExpect(jsonPath("$.[1].orderId").value(2L))
                .andExpect(jsonPath("$.[1].tableId").value(2L))
                .andExpect(jsonPath("$.[1].totalPrice").value(10.0))
                .andExpect(jsonPath("$.[1].tip").value(3.0))
                .andExpect(jsonPath("$.[1].dateTime").value("2021-06-02T10:29:06.582"))
                .andExpect(jsonPath("$.[2].orderId").value(3L))
                .andExpect(jsonPath("$.[2].tableId").value(3L))
                .andExpect(jsonPath("$.[2].totalPrice").value(12.0))
                .andExpect(jsonPath("$.[2].tip").value(3.0))
                .andExpect(jsonPath("$.[2].dateTime").value("2021-06-03T10:29:06.582"));
    }

    @Test
    void shouldGetOneOrder() throws Exception {
        mvc.perform(get("/orders/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(1L))
                .andExpect(jsonPath("$.tableId").value(1L))
                .andExpect(jsonPath("$.totalPrice").value(13.0))
                .andExpect(jsonPath("$.tip").value(3.0))
                .andExpect(jsonPath("$.dateTime").value("2021-06-01T10:29:06.582"));
    }

    @Test
    void UpdateOrderStatus() throws Exception {
        FoodOrder foodOrder = new FoodOrder(1l,2l, OrderStatus.Done,13.0,3.0,LocalDateTime.now());
        String OrderAsString = mapper.writeValueAsString(foodOrder);

        mvc.perform(put("/orders/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(OrderAsString)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(1L))
                .andExpect(jsonPath("$.tableId").value(2L))
                .andExpect(jsonPath("$.orderStatus").value("Done"))
                .andExpect(jsonPath("$.totalPrice").value(13.0))
                .andExpect(jsonPath("$.tip").value(3.0));
    }


}