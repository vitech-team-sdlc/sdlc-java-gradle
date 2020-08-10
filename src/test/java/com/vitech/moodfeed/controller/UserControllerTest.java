package com.vitech.moodfeed.controller;

import com.vitech.moodfeed.SmallTest;
import com.vitech.moodfeed.domain.User;
import com.vitech.moodfeed.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

class UserControllerTest extends SmallTest {

    @Mock
    private UserService userServiceMock;

    @InjectMocks
    private UserController userController;

    @Test
    void testGetLoggedUser() {
        // mock
        User expectedUser = testUser();
        when(userServiceMock.getLoggedUser()).thenReturn(expectedUser);
        // test
        User actualUser = userController.getLoggedUser();
        // verify
        assertSame(expectedUser, actualUser);
    }
}