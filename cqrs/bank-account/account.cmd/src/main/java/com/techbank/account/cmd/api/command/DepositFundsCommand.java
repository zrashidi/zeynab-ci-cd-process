package com.techbank.account.cmd.api.command;

import com.techbank.cqrs.core.command.BaseCommand;
import lombok.Data;

@Data
public class DepositFundsCommand  extends BaseCommand {
    private  double account;

}
