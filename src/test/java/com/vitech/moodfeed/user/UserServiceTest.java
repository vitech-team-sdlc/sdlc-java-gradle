package com.vitech.moodfeed.user;

import com.vitech.moodfeed.SmallTest;
import com.vitech.moodfeed.TestData;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class UserServiceTest extends SmallTest {

    @Mock
    private UserRepository userRepoMock;

    @InjectMocks
    private UserService userService;

    @Test
    void testGetLoggedUser() {
        // mock
        List<User> allUsers = TestData.users();
        when(userRepoMock.findAll()).thenReturn(allUsers);
        // test
        User loggedUser = userService.getLoggedUser();
        // verify
        assertNotNull(loggedUser);
        assertTrue(allUsers.contains(loggedUser));
    }
}
