package com.vitech.moodfeed;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public class WebSmallTest extends SmallTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    public MockMvc mockMvc() {
        return mockMvc;
    }

    @SneakyThrows
    public String toJson(Object object) {
        return objectMapper.writeValueAsString(object);
    }

}
