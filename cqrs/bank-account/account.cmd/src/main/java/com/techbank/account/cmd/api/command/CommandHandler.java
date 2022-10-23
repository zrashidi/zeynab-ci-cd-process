package com.techbank.account.cmd.api.command;

import com.techbank.account.common.dto.OrchestratorCommand;

public interface CommandHandler { //Colleague Class
    void  handler(OpenAccoundCommand command);
    void  handler(CloseAccountCommand command);
    void  handler(DepositFundsCommand command);
    void  handler(WithdrawFundsCommand command);
    void  handler(OrchestratorCommand command);
}
