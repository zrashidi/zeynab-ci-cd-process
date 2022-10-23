package com.techbank.account.query.api.queries;

import com.techbank.cqrs.core.domain.BaseEntity;

import java.util.List;

public interface QueryHandler {


    List<BaseEntity> handler(FindAccountByIdQuery query);
    List<BaseEntity> handler(FindAccountByHolderQuery query);
    List<BaseEntity> handler(FindAccountWithBalanceQuery query);
    List<BaseEntity> handler(FindAllAccountsQuery query);
}
