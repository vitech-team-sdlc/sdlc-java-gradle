package com.vitech.moodfeed.domain.user;

import com.vitech.moodfeed.SmallTest;
import com.vitech.moodfeed.TestData;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class UserServiceTest extends SmallTest {

    @Mock
    private UserRepository userRepoMock;

    @InjectMocks
    private UserService userService;

    @Test
    void testGetUsers() {
        // mock
        List<User> allUsers = TestData.users();
        when(userRepoMock.findAll()).thenReturn(allUsers);
        // test
        List<User> loggedUser = userService.getUsers();
        // verify
        assertNotNull(loggedUser);
        assertEquals(allUsers.size(), loggedUser.size());
    }
}
