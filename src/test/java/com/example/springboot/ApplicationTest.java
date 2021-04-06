package com.example.springboot;
import org.mockito.Mock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.nio.file.*;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testHistory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api?post_input_text=testing")).andReturn();
        mockMvc.perform(MockMvcRequestBuilders.get("/history").contentType(MediaType.ALL))
                .andExpect(content().string(containsString("testing")));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/delete?post_text=stringToDelete")).andReturn();
        mockMvc.perform(MockMvcRequestBuilders.post("/delete?post_text=stringToDelete").
                contentType(MediaType.ALL)).andExpect(content().string(containsString("does not exist")));
    }

    @Test
    void firstTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api?post_input_text=testing")).andReturn();
        mockMvc.perform(MockMvcRequestBuilders.post("/delete?post_text=testing")).andReturn();
        mockMvc.perform(MockMvcRequestBuilders.get("/history").contentType(MediaType.ALL))
                .andExpect(content().string(not(containsString("testing"))));
    }

    @Test
    void secondTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api?post_input_text=Testing")).andReturn();
        mockMvc.perform(MockMvcRequestBuilders.post("/api?post_input_text=testing")).andReturn();

        mockMvc.perform(MockMvcRequestBuilders.get("/delete?post_text=Testing").contentType(MediaType.ALL))
                .andExpect(content().string(not(containsString("testing"))));
        mockMvc.perform(MockMvcRequestBuilders.get("/delete?post_text=testing").contentType(MediaType.ALL))
                .andExpect(content().string(not(containsString("Testing"))));
    }
}