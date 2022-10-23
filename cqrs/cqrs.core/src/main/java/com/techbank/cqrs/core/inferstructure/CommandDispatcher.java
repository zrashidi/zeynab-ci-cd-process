package com.techbank.cqrs.core.inferstructure;

import com.techbank.cqrs.core.command.BaseCommand;
import com.techbank.cqrs.core.command.CommandHandlerMethod;

public interface CommandDispatcher { //Mediator

  <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handle);

   void send(BaseCommand command);


}
