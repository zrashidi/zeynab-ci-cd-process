package com.techbank.account.cmd.domin;

import com.techbank.account.cmd.api.command.OpenAccoundCommand;
import com.techbank.cqrs.core.event.EventModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventStoreRepository extends MongoRepository<EventModel, String> {
    List<EventModel> findByAggregateIdentifier(String aggregateIdentifier);


}
