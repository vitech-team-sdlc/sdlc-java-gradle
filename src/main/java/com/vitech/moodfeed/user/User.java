package com.vitech.moodfeed.user;

import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.Random;

@Value
@Builder
public class User {

    @Id Long id;
    String firstName;
    String lastName;
    String logoColor;

    public static User getRandom(UserRepository repo) {
        // get all users
        List<User> allUsers = Lists.newArrayList(repo.findAll());
        // return random one
        return allUsers.get(new Random().nextInt(allUsers.size()));
    }

}
