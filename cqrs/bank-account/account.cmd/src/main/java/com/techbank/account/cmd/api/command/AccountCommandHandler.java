package com.techbank.account.cmd.api.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techbank.account.cmd.domin.AccountAggregate;
import com.techbank.account.common.dto.OrchestratorCommand;
import com.techbank.account.common.dto.OrchestratorRequestDTO;
import com.techbank.cqrs.core.event.EventModel;
import com.techbank.cqrs.core.handlers.EventSouringHandler;
import com.techbank.cqrs.core.producers.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.stereotype.Service;
import reactor.core.publisher.FluxSink;

@Service
public class AccountCommandHandler implements  CommandHandler{ //Concrete Colleagues

    @Autowired
    private EventSouringHandler<AccountAggregate> eventSouringHandler;
    @Autowired
    private EventProducer eventProducer;


    @Autowired
    private FluxSink<OrchestratorRequestDTO> sink;

    @Override
    public void handler(OpenAccoundCommand command) {
        var aggregate= new AccountAggregate(command);
        eventSouringHandler.save(aggregate);

    }

    @Override
    public void handler(CloseAccountCommand command) {
        var aggregate=eventSouringHandler.getById(command.getId());
        aggregate.closeAccount();
        eventSouringHandler.save(aggregate);
    }

    @Override
    public void handler(DepositFundsCommand command) {
        var aggregate=eventSouringHandler.getById(command.getId());
        aggregate.depositFunds(command.getAccount());
        eventSouringHandler.save(aggregate);

    }

    @Override
    public void handler(WithdrawFundsCommand command) {
        var aggregate=eventSouringHandler.getById(command.getId());
       if (command.getAccount() >aggregate.getBalance()){
           throw  new IllegalStateException();
       }
       aggregate.withdrawFunds(command.getAccount());
       eventSouringHandler.save(aggregate);

    }

    @Override
    public void handler(OrchestratorCommand command) {
        OpenAccoundCommand openAccoundCommand= new OpenAccoundCommand();
        openAccoundCommand.setAccoundHolder(command.getAccoundHolder());
        openAccoundCommand.setAccountType(command.getAccountType());
        openAccoundCommand.setOpeningBalance(command.getOpeningBalance());
        openAccoundCommand.setId(command.getId());
        var aggregate= new AccountAggregate(openAccoundCommand);
         eventSouringHandler.save(aggregate);
        OrchestratorRequestDTO requestDTO= new OrchestratorRequestDTO();
        requestDTO.setAccoundHolder(command.getAccoundHolder());
        requestDTO.setAccountType(command.getAccountType());
        requestDTO.setOpeningBalance(command.getOpeningBalance());
        requestDTO.setAccountId(command.getId());
        requestDTO.setName(command.getName());
        requestDTO.setFamily(command.getFamily());
        requestDTO.setAge(command.getAge());

       ;
       // this.sink.next(requestDTO);
       eventProducer.produce("Account-created",requestDTO);
       // eventProducer.produce("Account-created",requestDTO);

    }
}
