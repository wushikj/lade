package com.wushi.lade.web;

import com.wushi.lade.LadeTestsApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collection;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = LadeTestsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class CorsTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void corsTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/cors/a"))
                .andReturn();
        Collection<String> headerNameList = mvcResult.getResponse().getHeaderNames();
        for (String name : headerNameList) {
            for (Object obj : mvcResult.getResponse().getHeaderValues(name))
                System.out.println(name + "==========" + obj.toString());
        }
        System.out.println("======" + mvcResult.getResponse().getContentAsString());
    }
}
