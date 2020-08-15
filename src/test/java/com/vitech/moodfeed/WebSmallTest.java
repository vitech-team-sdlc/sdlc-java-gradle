package com.vitech.moodfeed;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitech.moodfeed.hashtag.HashtagRepo;
import com.vitech.moodfeed.message.MessageRepo;
import com.vitech.moodfeed.user.UserRepo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public abstract class WebSmallTest extends SmallTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    protected MessageRepo messageRepo;

    @MockBean
    protected UserRepo userRepository;

    @MockBean
    protected HashtagRepo hashtagRepo;

    @SpyBean
    protected RepoRegistry registry;

    public MockMvc mockMvc() {
        return mockMvc;
    }

    @SneakyThrows
    public String toJson(Object object) {
        return objectMapper.writeValueAsString(object);
    }

}
