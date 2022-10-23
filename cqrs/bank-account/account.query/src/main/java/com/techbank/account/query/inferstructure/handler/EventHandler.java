package com.techbank.account.query.inferstructure.handler;

import com.techbank.account.common.events.*;

public interface EventHandler {
    void  on(AccountOpenEvent event);
    void  on(AccountCloseEvent event);
    void  on(FundsDepositEvent event);
    void  on(FundsWithdrawEvent event);
    void  on(UserEvent event);

}
