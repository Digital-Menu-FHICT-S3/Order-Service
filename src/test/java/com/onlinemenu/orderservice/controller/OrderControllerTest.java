package com.onlinemenu.orderservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinemenu.orderservice.entity.FoodOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

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

        FoodOrder OrderToPost = new FoodOrder(1l,2l,0,13.0,3.0,LocalDateTime.now());
        String OrderAsString = mapper.writeValueAsString(OrderToPost);

        String response = mvc.perform(post("/orders/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(OrderAsString)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(response.contains("1"));
        assertTrue(response.contains("2"));
        assertTrue(response.contains("0"));
        assertTrue(response.contains("13.0"));
        assertTrue(response.contains("3.0"));
    }
}