package com.vitech.moodfeed.domain.user;

import com.google.common.collect.ImmutableMap;
import com.vitech.moodfeed.SmallTest;
import com.vitech.moodfeed.TestData;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest extends SmallTest {

    private static final String TEST_AUTH_ID = "test_auth_id";

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

    @Test
    void testGetOrCreate_userWithAuthIdExist() {
        User testUser = TestData.users().get(0)
                .toBuilder()
                .authId(TEST_AUTH_ID)
                .build();
        Jwt testJwt = getUserJwt(testUser);
        // mock
        when(userRepoMock.findByAuthId(TEST_AUTH_ID))
                .thenReturn(Optional.of(testUser));
        // test
        User user = userService.getOrCreate(testJwt);
        // verify
        assertNotNull(user);
        assertEquals(TEST_AUTH_ID, user.getAuthId());
        assertEquals(testUser.getFirstName(), user.getFirstName());
        assertEquals(testUser.getLastName(), user.getLastName());
        verify(userRepoMock, never()).findByFirstNameAndLastName(any(), any());
        verify(userRepoMock, never()).save(any());
    }

    @Test
    void testGetOrCreate_userWithoutAuthIdExist() {
        User testUser = TestData.users().get(0);
        Jwt testJwt = getUserJwt(testUser);
        // mock
        when(userRepoMock.findByAuthId(TEST_AUTH_ID))
                .thenReturn(Optional.empty());
        when(userRepoMock.findByFirstNameAndLastName(
                testUser.getFirstName(), testUser.getLastName()
        )).thenReturn(Optional.of(testUser));
        when(userRepoMock.save(any()))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArguments()[0]);
        // test
        User user = userService.getOrCreate(testJwt);
        // verify
        assertNotNull(user);
        assertEquals(TEST_AUTH_ID, user.getAuthId());
        assertEquals(testUser.getFirstName(), user.getFirstName());
        assertEquals(testUser.getLastName(), user.getLastName());
    }

    @Test
    void testGetOrCreate_userNotExist() {
        User testUser = TestData.users().get(0);
        Jwt testJwt = getUserJwt(testUser);
        // mock
        when(userRepoMock.findByAuthId(TEST_AUTH_ID))
                .thenReturn(Optional.empty());
        when(userRepoMock.findByFirstNameAndLastName(any(), any()))
                .thenReturn(Optional.empty());
        when(userRepoMock.save(any()))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArguments()[0]);
        // test
        User user = userService.getOrCreate(testJwt);
        // verify
        assertNotNull(user);
        assertEquals(TEST_AUTH_ID, user.getAuthId());
        assertEquals(testUser.getFirstName(), user.getFirstName());
        assertEquals(testUser.getLastName(), user.getLastName());
    }

    private Jwt getUserJwt(User testUser) {
        Map<String, Object> headers = ImmutableMap.<String, Object> builder()
                .put("Access token", "test_access_token")
                .build();
        Map<String, Object> claims =  ImmutableMap.<String, Object> builder()
                .put("sub", TEST_AUTH_ID)
                .put("given_name", testUser.getFirstName())
                .put("family_name", testUser.getLastName())
                .build();
        Instant now = Instant.now();
        return new Jwt("test_token_value", now, now.plus(1, ChronoUnit.MINUTES), headers, claims);
    }

}
