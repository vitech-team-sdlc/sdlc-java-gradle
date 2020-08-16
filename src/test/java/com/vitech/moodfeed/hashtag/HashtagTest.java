package com.vitech.moodfeed.hashtag;

import com.vitech.moodfeed.RepoRegistry;
import com.vitech.moodfeed.message.dto.Request;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@Tag("smallTest")
@ExtendWith(MockitoExtension.class)
class HashtagTest {

    @Mock
    private HashtagRepo repo;

    @InjectMocks
    private RepoRegistry registry;

    @Test
    public void saveTags() {
        Request request = Request.builder().body("some #tag1 and maybe #tag2!").build();
        long messageId = 1L;
        Hashtag.saveTags(messageId, request, registry);
        verify(repo, times(1)).save(Hashtag.builder().tag("tag1").messageId(messageId).build());
        verify(repo, times(1)).save(Hashtag.builder().tag("tag2").messageId(messageId).build());
        verifyNoMoreInteractions(repo);
    }

    @Test
    public void findAllByMessageId() {
        long messageId = 1L;
        when(repo.findAllByMessageId(messageId)).thenReturn(
                new HashSet<>(asList(
                        Hashtag.builder().tag("tag1").build(),
                        Hashtag.builder().tag("tag2").build(),
                        Hashtag.builder().tag("tag3").build()
                ))
        );
        assertThat(Hashtag.findAllByMessageId(messageId, registry), containsInAnyOrder("tag1", "tag2", "tag3"));
        verify(repo, times(1)).findAllByMessageId(messageId);
        verifyNoMoreInteractions(repo);
    }

    @Test
    public void findTopUsed() {
        List<String> tags = asList("tag1", "tag2", "tag3");
        int limit = 5;
        when(repo.findTopUsed(limit)).thenReturn(tags);
        assertThat(Hashtag.findTopUsed(limit, registry), is(tags));
        verify(repo, times(1)).findTopUsed(limit);
        verifyNoMoreInteractions(repo);
    }
}