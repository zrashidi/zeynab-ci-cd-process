package com.user.account;

import com.techbank.cqrs.core.inferstructure.CommandDispatcher;
import com.user.account.api.command.CommandHandler;
import com.user.account.api.command.UserCommand;
import com.user.account.api.command.UserCommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class UserApplication {
    @Autowired
    private CommandDispatcher commandDispatcher;

    @Autowired
    private CommandHandler commandHandler;

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }


    @PostConstruct
    public void registerHandler() {
        commandDispatcher.registerHandler(UserCommand.class, commandHandler::handler);


    }
}