package com.vitech.moodfeed.user;

import com.google.common.collect.Lists;
import com.vitech.moodfeed.RepoRegistry;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.Random;

@Value
@Builder
public class User {

    @Id
    Long id;
    String firstName;
    String lastName;
    String logoColor;

    public static User getRandom(RepoRegistry registry) {
        // get all users
        List<User> allUsers = Lists.newArrayList(registry.getUserRepo().findAll());
        // return random one
        return allUsers.get(new Random().nextInt(allUsers.size()));
    }

    public static User findById(Long id, RepoRegistry registry) {
        return registry.getUserRepo().findById(id)
                .orElseThrow(() -> new RuntimeException("User by id = " + id + " not found!"));
    }

}
