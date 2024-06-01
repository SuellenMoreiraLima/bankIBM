package com.agency.bankibm.controller;

import com.agency.bankibm.dto.AccountDTO;
import com.agency.bankibm.model.*;
import com.agency.bankibm.repository.AccountRepository;
import com.agency.bankibm.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/account")
@CrossOrigin(origins = "*")
public class AccountController {

    private final AccountService accountService;

    @Autowired // Adiciona esta anotação para injetar o AccountService
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/deposit/{id}")
    public ResponseEntity<AccountDTO> depositar(@PathVariable int id, @RequestBody DepositRequest request) {
        try {
            // Chamar o método depositar do serviço passando o ID da conta e o valor do depósito
            AccountDTO accountDTO = accountService.depositar(id, request.getValorDeposito());
            return ResponseEntity.ok(accountDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/sacar/{id}")
    public ResponseEntity<AccountDTO> sacar(@PathVariable int id, @RequestBody WithdrawalRequest request) {
        try {
            // Chamar o método sacar do serviço passando o ID da conta e o valor do saque
            AccountDTO accountDTO = accountService.sacar(id, request.getValorSaque());
            return ResponseEntity.ok(accountDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }
    @GetMapping("/balance/{id}")
    public ResponseEntity<Double> getAccountBalance(@PathVariable int id) {
        try {
            double balance = accountService.getAccountBalance(id);
            return ResponseEntity.ok(balance);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/debit/{id}")
    public ResponseEntity<AccountDTO> debitar(@PathVariable int id, @RequestBody DebitoRequet request) {
//        try {
//            AccountDTO accountDTO = accountService.debito(id, request.getValorCompra());
//            return ResponseEntity.ok(accountDTO);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
        double valorDebito = request.getValorDebito();
        System.out.println("Valor de débito recebido: " + valorDebito);
        try {
            // Chamar o método debitar do serviço passando o ID da conta e o valor do débito
            AccountDTO accountDTO = accountService.debito(id, request.getValorDebito());
            return ResponseEntity.ok(accountDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/credit/{id}")
    public ResponseEntity<AccountDTO> creditar(@PathVariable int id, @RequestBody CreditoRequest request) {
        try {
            // Chamar o método creditar do serviço passando o ID da conta e o valor da compra
            AccountDTO accountDTO = accountService.credito(id, request.getValorCompra());
            return ResponseEntity.ok(accountDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

//    @GetMapping("/{accountId}")
//    public ResponseEntity<AccountDTO> getAccount(@PathVariable int accountId) {
//        Account account = accountService.get(accountId);
//        if (account != null) {
//            return ResponseEntity.ok(account.toDTO());
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

//    @GetMapping("/list")
//    public ResponseEntity<AccountDTO> getAccount(@PathVariable Long accountId) {
//        Account account = accountService.getAccountById(accountId);
//        if (account != null) {
//            return ResponseEntity.ok(account.toDTO());
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

//    @PostMapping
//    public ResponseEntity<AccountDTO> saveAccount(@RequestBody AccountDTO accountDTO) {
//        Account account = accountService.saveAccount(accountDTO);
//        return ResponseEntity.status(HttpStatus.CREATED).body(account.toDTO());
//    }
    @PostMapping
    public ResponseEntity<AccountDTO> saveAccount(@RequestBody AccountDTO accountDTO) {
    AccountDTO savedAccount = accountService.saveAccount(accountDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedAccount);
    }

//    @PutMapping("/{accountId}")
//    public ResponseEntity<AccountDTO> updateAccount(@PathVariable Long accountId, @RequestBody AccountDTO accountDTO) {
//        Account updatedAccount = accountService.updateAccount(accountId, accountDTO);
//        if (updatedAccount != null) {
//            return ResponseEntity.ok(updatedAccount.toDTO());
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @DeleteMapping("/{accountId}")
//    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {
//        accountService.deleteAccount(accountId);
//        return ResponseEntity.noContent().build();
//    }


}
