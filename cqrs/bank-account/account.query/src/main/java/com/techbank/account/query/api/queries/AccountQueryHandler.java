package com.techbank.account.query.api.queries;

import com.techbank.account.query.api.dto.EqualityType;
import com.techbank.account.query.domain.AccountRepository;
import com.techbank.account.query.domain.BankAccount;
import com.techbank.cqrs.core.domain.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountQueryHandler implements  QueryHandler{

@Autowired
    AccountRepository accountRepository;

    @Override
    public List<BaseEntity> handler(FindAccountByIdQuery query) {
        var bankAccount= accountRepository.findById(query.getId());
        if (bankAccount.isEmpty()){
            return null;

        }
        List<BaseEntity> bankAccountsList= new ArrayList<>();
        bankAccountsList.add(bankAccount.get());
        return bankAccountsList;

    }

    @Override
    public List<BaseEntity> handler(FindAccountByHolderQuery query) {
        var bankAccount= accountRepository.findByAccountHolder(query.getAccountHolder());
        if(bankAccount.isEmpty()) {
            return null;
        }
        List<BaseEntity> bankAccountsList= new ArrayList<>();
        bankAccountsList.add(bankAccount.get());
        return bankAccountsList;

    }

    @Override
    public List<BaseEntity> handler(FindAccountWithBalanceQuery query) {
        List<BaseEntity> bankAccountsList = query.getEqualityType() == EqualityType.GREATER_THAN
                ? accountRepository.findByBalanceGreaterThan(query.getBalance())
                : accountRepository.findByBalanceLessThan(query.getBalance());
        return bankAccountsList;
    }

    @Override
    public List<BaseEntity> handler(FindAllAccountsQuery query) {

        Iterable<BankAccount> bankAccounts= accountRepository.findAll();

        List<BaseEntity> bankAccountsList= new ArrayList<>();
        bankAccounts.forEach(bankAccountsList::add);
        return bankAccountsList;
    }
}
