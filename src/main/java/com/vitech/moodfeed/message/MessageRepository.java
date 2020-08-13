package com.vitech.moodfeed.message;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface MessageRepository extends PagingAndSortingRepository<Message, Long> {
}
