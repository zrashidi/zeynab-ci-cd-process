package com.techbank.account.cmd.api.controller;


import com.techbank.account.cmd.api.command.OpenAccoundCommand;
import com.techbank.account.cmd.api.dto.OpenAccountResponse;
import com.techbank.account.cmd.domin.EventStoreRepository;
import com.techbank.account.common.dto.BaseResponse;
import com.techbank.cqrs.core.inferstructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.channels.IllegalSelectorException;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/opi/v1/OpenBanckAccount")
public class OpenAccountController {

    private  final Logger logger= Logger.getLogger(OpenAccountController.class.getName());

    @Autowired
    CommandDispatcher commandDispatcher;


    @PostMapping
    public ResponseEntity<BaseResponse> openAccount(@RequestBody OpenAccoundCommand command){
       var id= UUID.randomUUID().toString();
       command.setId(id);
       try {
           command.setState("ACCOUNT_COMPLETED");
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

}
