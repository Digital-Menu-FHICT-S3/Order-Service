package com.onlinemenu.orderservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class OrderLineControllerTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    @Test
    void shouldGetOrderLinesFromOneOrder() throws Exception {
        mvc.perform(get("/orders/orderlines/fromorder/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].orderLineId").value(1L))
                .andExpect(jsonPath("$.[0].orderId").value(1L))
                .andExpect(jsonPath("$.[0].dishId").value(1L))
                .andExpect(jsonPath("$.[0].amount").value(3))
                .andExpect(jsonPath("$.[1].orderLineId").value(2L))
                .andExpect(jsonPath("$.[1].orderId").value(1L))
                .andExpect(jsonPath("$.[1].dishId").value(2L))
                .andExpect(jsonPath("$.[1].amount").value(2));
    }
}