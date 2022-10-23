package com.techbank.cqrs.core.inferstructure;

import com.techbank.cqrs.core.event.BaseEvent;
import com.techbank.cqrs.core.event.EventModel;

import java.util.List;

public interface EventStore {
    EventModel saveEvents(String aggregateIdentifier , Iterable<BaseEvent> events, int expectedVersion);

    List<BaseEvent> getEvents(String aggregateIdentifier);

}
