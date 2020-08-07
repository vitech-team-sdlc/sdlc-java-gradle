package com.vitech.moodfeed.repo;

import com.vitech.moodfeed.domain.Message;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MessageRepository extends PagingAndSortingRepository<Message, Long> {
}
