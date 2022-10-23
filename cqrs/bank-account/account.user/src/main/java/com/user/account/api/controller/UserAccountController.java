package com.user.account.api.controller;

import com.techbank.account.common.dto.BaseResponse;
import com.techbank.cqrs.core.inferstructure.CommandDispatcher;
import com.user.account.api.command.UserCommand;
import com.user.account.api.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.channels.IllegalSelectorException;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/opi/v1/user")
public class UserAccountController {

    private  final Logger logger= Logger.getLogger(UserAccountController.class.getName());

    @Autowired
    CommandDispatcher commandDispatcher;


    @PostMapping
    public ResponseEntity<BaseResponse> createUser(@RequestBody UserCommand command){
       var id= UUID.randomUUID().toString();
       command.setId(id);
       try {
           command.setState("ACCOUNT_COMPLETED");
           commandDispatcher.send(command);
           return  new ResponseEntity<>(new UserResponse("ACCOUNT_COMPLETED",id),HttpStatus.CREATED);

       }catch (IllegalSelectorException e){
           e.printStackTrace();
           return  new ResponseEntity<>(new BaseResponse("Error in saving user"),HttpStatus.BAD_REQUEST);
       }

       catch (Exception ex){
           ex.printStackTrace();
           return  new ResponseEntity<>(new BaseResponse("Error in saving user"),HttpStatus.BAD_REQUEST);
       }

    }
    @PostMapping("/update")
    public ResponseEntity<BaseResponse> updateUser(@RequestBody UserCommand command){
        var id= UUID.randomUUID().toString();
         command.setId(id);
        command.setState(" ACCOUNT_CANCELLED");
        try {
            commandDispatcher.send(command);
            return  new ResponseEntity<>(new UserResponse("ACCOUNT_COMPLETED",id),HttpStatus.CREATED);

        }catch (IllegalSelectorException e){
            e.printStackTrace();
            return  new ResponseEntity<>(new BaseResponse("Error in saving user"),HttpStatus.BAD_REQUEST);
        }

        catch (Exception ex){
            ex.printStackTrace();
            return  new ResponseEntity<>(new BaseResponse("Error in saving user"),HttpStatus.BAD_REQUEST);
        }

    }
}
