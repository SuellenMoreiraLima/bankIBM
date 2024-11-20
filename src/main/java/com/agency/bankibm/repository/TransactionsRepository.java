package com.agency.bankibm.repository;

import com.agency.bankibm.model.TransactionType;
import com.agency.bankibm.model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionsRepository extends JpaRepository<Transactions, Integer> {

    List<Transactions> findByAccountId(int accountId);

    Optional<Object> findByAccountIdAndType(int accountId, TransactionType transactionType);
}
