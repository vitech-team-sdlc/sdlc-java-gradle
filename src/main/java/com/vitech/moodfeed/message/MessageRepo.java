package com.vitech.moodfeed.message;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface MessageRepo extends PagingAndSortingRepository<Message, Long> {
}
