package com.vitech.moodfeed.domain.user;

import com.vitech.moodfeed.RepoMediumTest;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRepositoryIT extends RepoMediumTest<User> {

    @Autowired
    public UserRepositoryIT(UserRepository userRepo) {
        super(userRepo, new CrudTestDataProvider<>(
                User::getId,
                () -> User.builder().firstName("fn").lastName("ln").logoColor("#000000").build(),
                (id) -> User.builder().id(id).firstName("ufn").lastName("uln").logoColor("#111111").build()
        ));
    }

}
