package com.techbank.account.common.dto;

import com.techbank.cqrs.core.command.BaseCommand;
import lombok.Data;

@Data
public class OrchestratorCommand extends BaseCommand {
    private  String accoundHolder;
    private AccountType accountType;
    private double openingBalance;
    private   String accountId;
    private  String age;
    private String name;
    private String family;
    private String state;
}
