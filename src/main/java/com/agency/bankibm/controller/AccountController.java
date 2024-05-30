package com.agency.bankibm.controller;

import com.agency.bankibm.dto.AccountDTO;
import com.agency.bankibm.model.Account;
import com.agency.bankibm.repository.AccountRepository;
import com.agency.bankibm.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/account")
public class AccountController {

    private final AccountService accountService;

    @Autowired // Adiciona esta anotação para injetar o AccountService
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
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
