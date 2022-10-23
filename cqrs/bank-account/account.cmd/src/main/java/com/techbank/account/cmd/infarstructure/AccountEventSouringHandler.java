package com.techbank.account.cmd.infarstructure;

import com.techbank.account.cmd.domin.AccountAggregate;
import com.techbank.cqrs.core.domain.AggregateRoot;
import com.techbank.cqrs.core.handlers.EventSouringHandler;
import com.techbank.cqrs.core.inferstructure.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class AccountEventSouringHandler implements EventSouringHandler<AccountAggregate> {


    @Autowired
   private   EventStore eventStore;

    @Override
    public void save(AggregateRoot root) {
        eventStore.saveEvents(root.getId(),root.getUnComittedChannges(),root.getVersion());
        root.markChanngesAsComitted();

    }

    @Override
    public AccountAggregate getById(String id) {
        var aggregate= new AccountAggregate();
             var   events=eventStore.getEvents(id);
             if(events !=null || !events.isEmpty()){
                 aggregate.replayEvent(events);
                 var lastVersion=events.stream().map(x-> x.getVersion()).max(Comparator.naturalOrder());
                 aggregate.setVersion(lastVersion.get());
             }


        return aggregate;
    }
}
