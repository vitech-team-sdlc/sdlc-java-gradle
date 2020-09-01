package com.vitech.moodfeed.domain.user;

import com.vitech.moodfeed.TestData;
import com.vitech.moodfeed.WebSmallTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest extends WebSmallTest {

    @MockBean
    private UserService userServiceMock;

    @Test
    void testGetLoggedUser() throws Exception {
        // mock
        User expectedUser = TestData.user();
        when(userServiceMock.getLoggedUser()).thenReturn(expectedUser);
        // test and verify
        mockMvc()
                .perform(get("/users/auth"))
                .andExpect(status().isOk())
                .andExpect(content().string(toJson(expectedUser)));
    }
}