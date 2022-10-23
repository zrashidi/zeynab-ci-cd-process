package com.techbank.account.query.inferstructure.handler;

import com.techbank.account.common.events.*;
import com.techbank.account.query.domain.AccountRepository;
import com.techbank.account.query.domain.BankAccount;
import com.techbank.account.query.domain.User;
import com.techbank.account.query.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountEventHandler  implements  EventHandler{

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public void on(AccountOpenEvent event) {

        var banckAccount= BankAccount.builder()
                .id(event.getId())
                .accountHolder(event.getAccoundHolder())
                .createDate(event.getCreateDate())
                .accountType(event.getAccountType())
                .balance(event.getOpeningBalance())
                .build();
        accountRepository.save(banckAccount);

    }

    @Override
    public void on(AccountCloseEvent event) {
        accountRepository.deleteById(event.getId());

    }

    @Override
    public void on(FundsDepositEvent event) {
        var banckAccount=accountRepository.findById(event.getId());
        if(banckAccount.isEmpty()){
            return;

        }
        var currentBalance= banckAccount.get().getBalance();
        var lastBalance=currentBalance+event.getAmount();
        banckAccount.get().setBalance(lastBalance);
        accountRepository.save(banckAccount.get());

    }

    @Override
    public void on(FundsWithdrawEvent event) {
        var banckAccount=accountRepository.findById(event.getId());
        if(banckAccount.isEmpty()){
            return;

        }
        var currentBalance= banckAccount.get().getBalance();
        var lastBalance=currentBalance - event.getAmount();
        banckAccount.get().setBalance(lastBalance);
        accountRepository.save(banckAccount.get());
    }

    @Override
    public void on(UserEvent event) {
        var user= User.builder()
                .id(event.getId())
                .name(event.getName())
                .family(event.getFamily())
                .state(event.getState())
                .build();
     userRepository.save(user);
    }
}
