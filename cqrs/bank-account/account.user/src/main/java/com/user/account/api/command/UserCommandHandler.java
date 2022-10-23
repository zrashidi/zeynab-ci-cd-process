package com.user.account.api.command;


import com.techbank.cqrs.core.handlers.EventSouringHandler;
import com.user.account.domin.UserAggregate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCommandHandler implements  CommandHandler{ //Concrete Colleagues

    @Autowired
    private EventSouringHandler<UserAggregate> eventSouringHandler;

    @Override
    public void handler(UserCommand command) {
        var aggregate= new UserAggregate(command);
        eventSouringHandler.save(aggregate);

    }


}
