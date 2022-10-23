package com.techbank.account.common.events;

import com.techbank.cqrs.core.event.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserEvent extends BaseEvent {
    private  String accoundHolder;
    private String name;
    private String family;
    private  String state;
}
