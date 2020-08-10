package com.vitech.moodfeed;

import com.vitech.moodfeed.domain.Message;
import com.vitech.moodfeed.domain.User;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Tag("smallTest")
@ExtendWith(MockitoExtension.class)
@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
public class SmallTest {

    public List<User> testUsers() {
        return Arrays.asList(
                User.builder().id(1L).firstName("Homer").lastName("Simpson").logoColor("#faf7af").build(),
                User.builder().id(2L).firstName("Marge").lastName("Simpson").logoColor("#b167e3").build(),
                User.builder().id(3L).firstName("Bart").lastName("Simpson").logoColor("#fad8af").build(),
                User.builder().id(4L).firstName("Lisa").lastName("Simpson").logoColor("#f0c5eb").build(),
                User.builder().id(5L).firstName("Ned").lastName("Flanders").logoColor("#afe0fa").build()
        );
    }

    public User testUser() {
        return testUsers().get(0);
    }

    public List<Message> testMessages() {
        return Arrays.asList(
                Message.builder().id(101L).message("test-message-1").creatorId(1L).createdAt(new Date()).build(),
                Message.builder().id(102L).message("test-message-2").creatorId(2L).createdAt(new Date()).build(),
                Message.builder().id(103L).message("test-message-3").creatorId(3L).createdAt(new Date()).build(),
                Message.builder().id(104L).message("test-message-4").creatorId(4L).createdAt(new Date()).build(),
                Message.builder().id(105L).message("test-message-5").creatorId(5L).createdAt(new Date()).build()
        );
    }

    public Message testMessage() {
        return testMessages().get(0);
    }

}
