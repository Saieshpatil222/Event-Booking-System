package com.event.repository;

import com.event.entity.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {

    Event findByEventName(String eventName);

}
