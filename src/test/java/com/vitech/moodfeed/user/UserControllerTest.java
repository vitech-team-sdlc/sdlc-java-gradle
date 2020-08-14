package com.vitech.moodfeed.user;

import com.vitech.moodfeed.WebSmallTest;
import lombok.SneakyThrows;
import org.hamcrest.collection.IsIn;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest extends WebSmallTest {

    @MockBean
    private UserRepository repoMock;

    @SneakyThrows
    @Test
    void testGetLoggedUser() {
        // mock
        List<User> users = UserTest.users();
        when(repoMock.findAll()).thenReturn(users);
        // test and verify
        mockMvc()
                .perform(get("/users/auth"))
                .andExpect(status().isOk())
                .andExpect(content().string(new IsIn<>(users.stream().map(this::toJson).collect(toList()))));
    }
}