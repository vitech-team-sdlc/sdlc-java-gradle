package com.vitech.moodfeed.domain.hashtag;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HashtagService {

    private final HashtagRepository hashtagRepo;

    public List<String> findTopUsed(int limit) {
        return hashtagRepo.findTopUsed(limit);
    }

}
