package com.vitech.moodfeed.domain.user;

import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final String DEFAULT_LOGO_COLOR = "#afafaf";

    private final UserRepository userRepo;

    public User getOrCreate(Jwt jwt) {
        final String authId = jwt.getClaimAsString("sub");
        return userRepo
                .findByAuthId(authId)
                .orElseGet(() -> userRepo
                        .findByFirstNameAndLastName(
                                jwt.getClaimAsString("given_name"),
                                jwt.getClaimAsString("family_name")
                        )
                        .map(user -> userRepo.save(user.toBuilder()
                                .authId(authId)
                                .build()))
                        .orElseGet(() -> userRepo.save(User.builder()
                                .authId(authId)
                                .firstName(jwt.getClaimAsString("given_name"))
                                .lastName(jwt.getClaimAsString("family_name"))
                                .logoColor(DEFAULT_LOGO_COLOR)
                                .build())
                        ))
                ;
    }

    public List<User> getUsers() {
        return ImmutableList.copyOf(userRepo.findAll());
    }

}
