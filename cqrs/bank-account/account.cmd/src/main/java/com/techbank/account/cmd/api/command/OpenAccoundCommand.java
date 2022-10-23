package com.techbank.account.cmd.api.command;

import com.techbank.account.common.dto.AccountType;
import com.techbank.cqrs.core.command.BaseCommand;
import lombok.Data;

@Data
public class OpenAccoundCommand  extends BaseCommand {

    private  String accoundHolder;
    private AccountType accountType;
    private double openingBalance;
    private  String state;
}
