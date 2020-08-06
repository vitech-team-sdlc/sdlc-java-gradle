package com.vitech.moodfeed.service;

import com.vitech.moodfeed.domain.User;

public interface UserService {

    /**
     * Get currently logged user
     *
     * @return currently logged user
     */
    User getLoggedUser();

}
