package com.techbank.account.cmd.domin;

import com.techbank.account.cmd.api.command.OpenAccoundCommand;
import com.techbank.account.common.events.AccountCloseEvent;
import com.techbank.account.common.events.AccountOpenEvent;
import com.techbank.account.common.events.FundsDepositEvent;
import com.techbank.account.common.events.FundsWithdrawEvent;
import com.techbank.cqrs.core.domain.AggregateRoot;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
//@AllArgsConstructor
public class AccountAggregate  extends AggregateRoot {
    private  Boolean active;
    private double balance;

    public double getBalance() {
        return balance;
    }

    public  AccountAggregate(OpenAccoundCommand command){
        raidEvent(AccountOpenEvent.builder()
                .id(command.getId())
                .accoundHolder(command.getAccoundHolder())
                .createDate(new Date())
                .accountType(command.getAccountType())
                .openingBalance(command.getOpeningBalance())
                .build());

    }
    public void apply(AccountOpenEvent event) {
        this.id = event.getId();
        this.active = true;
        this.balance = event.getOpeningBalance();
    }

    public void depositFunds(double amount) {
        if (!this.active) {
            throw new IllegalStateException("Funds cannot be deposited into a closed account!");
        }
        if(amount <= 0) {
            throw new IllegalStateException("The deposit amount must be greater than 0!");
        }
        raidEvent(FundsDepositEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }

    public void apply(FundsDepositEvent event) {
        this.id = event.getId();
        this.balance += event.getAmount();
    }

    public void withdrawFunds(double amount) {
        if (!this.active) {
            throw new IllegalStateException("Funds cannot be withdrawn from a closed account!");
        }
        raidEvent(FundsWithdrawEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }

    public void apply(FundsWithdrawEvent event) {
        this.id = event.getId();
        this.balance -= event.getAmount();
    }

    public void closeAccount() {
        if (!this.active) {
            throw new IllegalStateException("The bank account has already been closed!");
        }
        raidEvent(AccountCloseEvent.builder()
                .id(this.id)
                .build());
    }

    public void apply(AccountCloseEvent event) {
        this.id = event.getId();
        this.active = false;
    }
}
