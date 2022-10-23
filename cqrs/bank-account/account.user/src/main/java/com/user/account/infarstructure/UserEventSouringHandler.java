package com.user.account.infarstructure;


import com.techbank.cqrs.core.domain.AggregateRoot;
import com.techbank.cqrs.core.handlers.EventSouringHandler;
import com.techbank.cqrs.core.inferstructure.EventStore;
import com.user.account.domin.UserAggregate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class UserEventSouringHandler implements EventSouringHandler<UserAggregate> {


    @Autowired
   private   EventStore eventStore;

    @Override
    public void save(AggregateRoot root) {
        eventStore.saveEvents(root.getId(),root.getUnComittedChannges(),root.getVersion());
        root.markChanngesAsComitted();

    }

    @Override
    public UserAggregate getById(String id) {
        var aggregate= new UserAggregate();
             var   events=eventStore.getEvents(id);
             if(events !=null || !events.isEmpty()){
                 aggregate.replayEvent(events);
                 var lastVersion=events.stream().map(x-> x.getVersion()).max(Comparator.naturalOrder());
                 aggregate.setVersion(lastVersion.get());
             }


        return aggregate;
    }
}
