package com.techbank.account.cmd.infarstructure;

import com.techbank.account.cmd.domin.AccountAggregate;
import com.techbank.account.cmd.domin.EventStoreRepository;
import com.techbank.cqrs.core.Exception.AggregateNotFoundException;
import com.techbank.cqrs.core.Exception.ConcurrencyException;
import com.techbank.cqrs.core.event.BaseEvent;
import com.techbank.cqrs.core.event.EventModel;
import com.techbank.cqrs.core.inferstructure.EventStore;
import com.techbank.cqrs.core.producers.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountEventStore  implements EventStore {

    @Autowired
   private EventProducer eventProducer;


    @Autowired
    EventStoreRepository repository;

//    Let's suppose we are using transactions to solve the issue of consistency between MongoDB and Kafka.
// MongoDB as part of a replica set
    @Transactional
    @Override
    public EventModel saveEvents(String aggregateIdentifier, Iterable<BaseEvent> events, int expectedVersion) {
      var eventStream=  repository.findByAggregateIdentifier(aggregateIdentifier);
      if(expectedVersion!=-1 && eventStream.get(eventStream.size()-1).getVersion() !=expectedVersion){
           throw  new ConcurrencyException();
      }
      var version=expectedVersion;
      for(var event:events){
          version++;
          event.setVersion(version);
          var eventModel= EventModel.builder()
                  .timestamp(new Date())
                  .aggregateIdentifier(aggregateIdentifier)
                  .aggregateType(AccountAggregate.class.getTypeName())
                  .version(version)
                  .eventType(event.getClass().getTypeName())
                  .eventData(event)

                  .build();
          var persistedEvent=repository.save(eventModel);
          if ((persistedEvent !=null)){
              // TODO produce for KAFKA
             // if(event.getClass().getTypeName().endsWith("EventModel"))
              eventProducer.produce(event.getClass().getSimpleName(),event);
              return persistedEvent ;
          }
      }
      return null;
    }

    @Override
    public List<BaseEvent> getEvents(String aggregateIdentifier) {
        var eventStream=  repository.findByAggregateIdentifier(aggregateIdentifier);
        if (eventStream==null || eventStream.isEmpty()){
            throw  new AggregateNotFoundException("Incorrect Account Id Provider");

        }
        return eventStream.stream().map(x ->x.getEventData()).collect(Collectors.toList());
    }
}
