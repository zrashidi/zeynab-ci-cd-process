package com.user.account.api.command;

import com.techbank.account.common.dto.AccountType;
import com.techbank.cqrs.core.command.BaseCommand;
import lombok.Data;

@Data
public class UserCommand extends BaseCommand {

    private  String age;
    private String name;
    private String family;
    private  String state;
    private   String accountId;

}
