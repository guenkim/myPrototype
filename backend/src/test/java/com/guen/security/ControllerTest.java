package com.guen.security;

import com.guen.program.member.controller.MemberController;
import com.guen.program.todo.api.TodoController;
import org.apache.catalina.filters.ExpiresFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String accessToken;
    @BeforeEach
    public void before(){
        accessToken = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI3MzM4Yjk0MC1jOWIwLTQ1ZTktYWNkOS00YTVjOTVmYjdhYjA6VVNFUiIsImlzcyI6ImdldW4iLCJpYXQiOjE3MDk2MjAzMTcsImV4cCI6MTcwOTYyMjExN30.GJqo88aptcWQ9NG8YNaQtkYp6rZ2iUmHFgDtEy6ucayJFZbOKq606C-hmezO0DjhK24iAFMSoaaiYSEBOHWYvw";
    }
    @Test
    @WithCustomMockUser
    public void test() throws Exception{

        mockMvc.perform(get("/api/member")
                        .header("Authorization", accessToken))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
