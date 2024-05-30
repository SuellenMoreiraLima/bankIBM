// TransactionsService.java
package com.agency.bankibm.service;

import com.agency.bankibm.dto.TransactionsDTO;
import com.agency.bankibm.model.Transactions;
import com.agency.bankibm.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionsService {

    private final TransactionsRepository transactionsRepository;

    @Autowired
    public TransactionsService(TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }

    public List<TransactionsDTO> getAllTransactions() {
        return transactionsRepository.findAll()
                .stream()
                .map(Transactions::toDTO)
                .collect(Collectors.toList());
    }

    public TransactionsDTO getTransactionById(int id) {
        Optional<Transactions> optionalTransaction = transactionsRepository.findById(id);
        return optionalTransaction.map(Transactions::toDTO).orElse(null);
    }

    public TransactionsDTO addTransaction(TransactionsDTO transactionsDTO) {
        Transactions transaction = transactionsRepository.save(transactionsDTO.toEntity());
        return transaction.toDTO();
    }

    public TransactionsDTO updateTransaction(int id, TransactionsDTO transactionsDTO) {
        Optional<Transactions> optionalTransaction = transactionsRepository.findById(id);
        if (optionalTransaction.isPresent()) {
            Transactions transaction = optionalTransaction.get();
            // Atualize os campos necessários da transação com base no DTO recebido
            // transaction.setXXX(transactionsDTO.getXXX());
            // ...
            return transactionsRepository.save(transaction).toDTO();
        } else {
            return null;
        }
    }

    public void deleteTransaction(int id) {
        transactionsRepository.deleteById(id);
    }
}
