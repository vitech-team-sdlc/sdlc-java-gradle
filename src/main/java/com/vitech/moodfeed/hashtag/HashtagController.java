package com.vitech.moodfeed.hashtag;

import com.vitech.moodfeed.RepoRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hashtags")
@RequiredArgsConstructor
public class HashtagController {

    private final RepoRegistry registry;

    @GetMapping
    public List<String> findTopUsed(@RequestParam(defaultValue = "10") int limit) {
        return Hashtag.findTopUsed(limit, registry);
    }
}
