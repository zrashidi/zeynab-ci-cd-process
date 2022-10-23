package com.user.account.domin;


import com.techbank.account.common.events.*;
import com.techbank.cqrs.core.domain.AggregateRoot;
import com.user.account.api.command.UserCommand;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
//@AllArgsConstructor
public class UserAggregate extends AggregateRoot {



    public UserAggregate(UserCommand command){
        raidEvent(UserEvent.builder()
                .id(command.getId())
                .accoundHolder(command.getAge())
                .name(command.getName())
                .family(command.getFamily())
                .state(command.getState())
                .build());

    }
    public void apply(UserEvent event) {
        this.id = event.getId();

    }


}
