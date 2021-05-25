package com.wushi.lade.web;

import com.wushi.lade.LadeTestsApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author wushi
 * @date 2020/1/20 14:56
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = LadeTestsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class MaliciousFileFilterTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testMaliciousFilterTest() throws Exception {
        File file = ResourceUtils.getFile("classpath:11.png");
        String filePath = file.getPath();
        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/malicious_file/upload")
                        .file(new MockMultipartFile("file", "11.png",
                                null,
                                new FileInputStream(file)));
        MvcResult result = mockMvc.perform(builder).andReturn();
        System.out.println("==================" + result.getResponse().getContentAsString());
    }
}
