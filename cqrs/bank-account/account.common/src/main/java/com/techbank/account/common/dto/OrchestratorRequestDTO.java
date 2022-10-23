package com.techbank.account.common.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.techbank.account.common.dto.AccountType;
import com.techbank.cqrs.core.event.BaseEvent;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonDeserialize
@JsonSerialize
public class OrchestratorRequestDTO extends BaseEvent implements Serializable {
    private  String accoundHolder;
    private AccountType accountType;
    private double openingBalance;
    private   String accountId;
    private  String age;
    private String name;
    private String family;
    private  String state;
}
