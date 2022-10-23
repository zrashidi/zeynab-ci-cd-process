package com.techbank.cqrs.core.command;

import com.techbank.cqrs.core.messages.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BaseCommand  extends Message {

    public BaseCommand(String id) {
        super(id);
        System.out.println("test git");

    }
}
