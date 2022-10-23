package com.techbank.account.cmd;

import com.techbank.account.cmd.api.command.*;
//import com.techbank.account.cmd.api.config.MongoConfig;
import com.techbank.account.common.dto.OrchestratorCommand;
import com.techbank.cqrs.core.inferstructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;

@SpringBootApplication
//@Import({ MongoConfig.class })
public class CommandApplication {

	@Autowired
  private 	CommandDispatcher commandDispatcher;

	@Autowired
	 private  CommandHandler commandHandler;


	public static void main(String[] args) {
		SpringApplication.run(CommandApplication.class, args);
	}

	@PostConstruct
	 public void registerHandler(){
		commandDispatcher.registerHandler(OpenAccoundCommand.class,commandHandler::handler);
		commandDispatcher.registerHandler(CloseAccountCommand.class,commandHandler::handler);
		commandDispatcher.registerHandler(DepositFundsCommand.class,commandHandler::handler);
		commandDispatcher.registerHandler(WithdrawFundsCommand.class,commandHandler::handler);
		commandDispatcher.registerHandler(OrchestratorCommand.class,commandHandler::handler);

	}

}
