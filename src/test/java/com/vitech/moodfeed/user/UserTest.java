package com.vitech.moodfeed.user;

import com.vitech.moodfeed.RepoRegistry;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@Tag("smallTest")
@ExtendWith(MockitoExtension.class)
public class UserTest {

    @Mock
    private UserRepo repoMock;

    @InjectMocks
    private RepoRegistry registry;

    public static List<User> users() {
        return Arrays.asList(
                User.builder().id(1L).firstName("Homer").lastName("Simpson").logoColor("#faf7af").build(),
                User.builder().id(2L).firstName("Marge").lastName("Simpson").logoColor("#b167e3").build(),
                User.builder().id(3L).firstName("Bart").lastName("Simpson").logoColor("#fad8af").build(),
                User.builder().id(4L).firstName("Lisa").lastName("Simpson").logoColor("#f0c5eb").build(),
                User.builder().id(5L).firstName("Ned").lastName("Flanders").logoColor("#afe0fa").build()
        );
    }

    @Test
    void testGetRandom() {
        // mock
        List<User> testUsers = users();
        when(repoMock.findAll()).thenReturn(testUsers);
        // test
        User loggedUser = User.getRandom(registry);
        // verify
        assertNotNull(loggedUser);
        assertTrue(testUsers.contains(loggedUser));
    }
}