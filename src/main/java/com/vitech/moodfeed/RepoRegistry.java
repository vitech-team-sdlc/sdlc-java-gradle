package com.vitech.moodfeed;

import com.vitech.moodfeed.hashtag.HashtagRepo;
import com.vitech.moodfeed.message.MessageRepo;
import com.vitech.moodfeed.user.UserRepo;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class RepoRegistry {

    private final MessageRepo messageRepo;
    private final UserRepo userRepo;
    private final HashtagRepo hashtagRepo;

}
