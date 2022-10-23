package com.techbank.account.cmd.api.controller;


import com.techbank.account.cmd.api.command.OpenAccoundCommand;
import com.techbank.account.cmd.api.dto.OpenAccountResponse;
import com.techbank.account.cmd.domin.EventStoreRepository;
import com.techbank.account.common.dto.BaseResponse;
import com.techbank.account.common.dto.OrchestratorCommand;
import com.techbank.cqrs.core.inferstructure.CommandDispatcher;
import com.techbank.cqrs.core.producers.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.FluxSink;

import java.nio.channels.IllegalSelectorException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/openAccountAndSaveUser")
public class OpenAccountAndUserController {

    @Autowired
    CommandDispatcher commandDispatcher;

    @Autowired
    EventStoreRepository eventStoreRepository;
    @PostMapping
    public ResponseEntity<BaseResponse> openAccount(@RequestBody OrchestratorCommand command){

       //  eventProducer.produce("Account-created",command);
        var id= UUID.randomUUID().toString();
        command.setId(id);
        try {
            command.setState("ACCOUNT_PENDING");
            //PENDING
            commandDispatcher.send(command);
            return  new ResponseEntity<>(new OpenAccountResponse("openAccount",id),HttpStatus.CREATED);

        }catch (IllegalSelectorException e){
            e.printStackTrace();
            return  new ResponseEntity<>(new BaseResponse("Error in saving openAccount"),HttpStatus.BAD_REQUEST);
        }

        catch (Exception ex){
            ex.printStackTrace();
            return  new ResponseEntity<>(new BaseResponse("Error in saving openAccount"),HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/delete")
    public ResponseEntity<BaseResponse> deleteAccount(@RequestBody OpenAccoundCommand command){
        try {
            command.setState("ACCOUNT_CANCELLED");
            commandDispatcher.send(command);
            return  new ResponseEntity<>(new OpenAccountResponse("openAccount","0"),HttpStatus.CREATED);

        }catch (IllegalSelectorException e){
            e.printStackTrace();
            return  new ResponseEntity<>(new BaseResponse("Error in saving openAccount"),HttpStatus.BAD_REQUEST);
        }

        catch (Exception ex){
            ex.printStackTrace();
            return  new ResponseEntity<>(new BaseResponse("Error in saving openAccount"),HttpStatus.BAD_REQUEST);
        }

    }
    @PostMapping("/update")
    public ResponseEntity<BaseResponse> updateAccount(@RequestBody OpenAccoundCommand command){
        try {
            command.setState("ACCOUNT_CANCELLED");
            commandDispatcher.send(command);
       System.out.println("************** Update ****************");
            return  new ResponseEntity<>(new OpenAccountResponse("openAccount","0"),HttpStatus.CREATED);

        }catch (IllegalSelectorException e){
            e.printStackTrace();
            return  new ResponseEntity<>(new BaseResponse("Error in saving openAccount"),HttpStatus.BAD_REQUEST);
        }

        catch (Exception ex){
            ex.printStackTrace();
            return  new ResponseEntity<>(new BaseResponse("Error in saving openAccount"),HttpStatus.BAD_REQUEST);
        }

    }
}
