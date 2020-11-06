package com.vitech.moodfeed.domain.user;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByAuthId(String authId);

    Optional<User> findByFirstNameAndLastName(String firstName, String lastName);
}
