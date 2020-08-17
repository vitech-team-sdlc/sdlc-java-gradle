package com.vitech.moodfeed.user;

import com.vitech.moodfeed.RepoMediumTest;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRepositoryIT extends RepoMediumTest<User> {

    @Autowired
    public UserRepositoryIT(UserRepository userRepository) {
        super(userRepository, new CrudTestDataProvider<>(
                User::getId,
                () -> User.builder().firstName("fn").lastName("ln").logoColor("#000000").build(),
                (id) -> User.builder().id(id).firstName("ufn").lastName("uln").logoColor("#111111").build()
        ));
    }

}
