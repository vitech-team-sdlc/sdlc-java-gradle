package com.vitech.moodfeed.domain.hashtag;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface HashtagRepository extends CrudRepository<Hashtag, Long> {

    Set<Hashtag> findAllByMessageId(Long messageId);

    @Query("select tag from hashtag group by tag order by count(1) desc, tag asc limit :limit")
    List<String> findTopUsed(@Param("limit") Integer limit);

}
