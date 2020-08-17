package com.vitech.moodfeed.user;

public interface UserService {

    /**
     * Get currently logged user
     *
     * @return currently logged user
     */
    User getLoggedUser();

    /**
     * Find user by id
     *
     * @param id user identifier
     * @return user found by provided id
     */
    User findById(Long id);

}
