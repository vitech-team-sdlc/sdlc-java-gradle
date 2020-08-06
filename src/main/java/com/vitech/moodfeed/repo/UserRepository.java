package com.vitech.moodfeed.repo;

import com.vitech.moodfeed.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
