package com.vitech.moodfeed.repo;

import com.vitech.moodfeed.domain.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Integer> {
}
