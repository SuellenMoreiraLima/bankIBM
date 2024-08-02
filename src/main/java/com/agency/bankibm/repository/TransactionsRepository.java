// Definição do pacote
package com.agency.bankibm.repository;

// Importações necessárias
import com.agency.bankibm.model.TransactionType;
import com.agency.bankibm.model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// Interface que estende JpaRepository para fornecer operações CRUD para a entidade Transactions
public interface TransactionsRepository extends JpaRepository<Transactions, Integer> {

    // Método para encontrar transações por ID de conta
    List<Transactions> findByAccountId(int accountId);

    // Método para encontrar transações por ID de conta e tipo de transação
    Optional<Object> findByAccountIdAndType(int accountId, TransactionType transactionType);
}
