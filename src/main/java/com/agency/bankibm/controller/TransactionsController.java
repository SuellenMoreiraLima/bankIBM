// TransactionsController.java
package com.agency.bankibm.controller;

import com.agency.bankibm.dto.TransactionsDTO;
import com.agency.bankibm.model.Transactions;
import com.agency.bankibm.repository.TransactionsRepository;
import com.agency.bankibm.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transactions")
@CrossOrigin(origins = "*")
public class TransactionsController {

    private final TransactionsService transactionsService;
    private final TransactionsRepository transactionsRepository;

    @Autowired
    public TransactionsController(TransactionsService transactionsService, TransactionsRepository transactionsRepository) {
        this.transactionsService = transactionsService;
        this.transactionsRepository = transactionsRepository;
    }

    @GetMapping
    public ResponseEntity<List<TransactionsDTO>> getAllTransactions() {
        List<TransactionsDTO> transactions = transactionsService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }
    @GetMapping("/historico/{accountId}")
    public ResponseEntity<List<TransactionsDTO>> getHistoricoTransacoes(@PathVariable int accountId) {
        List<Transactions> transacoes = transactionsRepository.findByAccountId(accountId);
        List<TransactionsDTO> transacoesDTO = transacoes.stream().map(Transactions::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(transacoesDTO);
    }


    @GetMapping("/{id}")
    public ResponseEntity<TransactionsDTO> getTransactionById(@PathVariable int id) {
        TransactionsDTO transaction = transactionsService.getTransactionById(id);
        if (transaction != null) {
            return ResponseEntity.ok(transaction);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<TransactionsDTO> addTransaction(@RequestBody TransactionsDTO transactionsDTO) {
        TransactionsDTO savedTransaction = transactionsService.addTransaction(transactionsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTransaction);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionsDTO> updateTransaction(@PathVariable int id, @RequestBody TransactionsDTO transactionsDTO) {
        TransactionsDTO updatedTransaction = transactionsService.updateTransaction(id, transactionsDTO);
        if (updatedTransaction != null) {
            return ResponseEntity.ok(updatedTransaction);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable int id) {
        transactionsService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}
