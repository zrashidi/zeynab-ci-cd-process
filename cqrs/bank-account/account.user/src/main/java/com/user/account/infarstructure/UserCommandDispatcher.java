package com.user.account.infarstructure;

import com.techbank.cqrs.core.command.BaseCommand;
import com.techbank.cqrs.core.command.CommandHandlerMethod;
import com.techbank.cqrs.core.inferstructure.CommandDispatcher;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class UserCommandDispatcher implements CommandDispatcher { // Concrete Mediator

    private final Map<Class<? extends  BaseCommand>, List<CommandHandlerMethod>> routs=  new HashMap<>();

    @Override
    public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handle) {
        var  handlers=routs.computeIfAbsent(type,c-> new LinkedList<>());
        handlers.add(handle);

    }

    @Override
    public void send(BaseCommand command) {
        var  handlers=routs.get(command.getClass());

        if (handlers==null|| handlers.size()==0 ){
            throw new RuntimeException(" Cannot Command handler was register");
        }
        if (handlers.size()>1){
            throw new RuntimeException(" Cannot send to be more handler");
        }
        // Functional method
     handlers.get(0).handle(command);
    }
}
