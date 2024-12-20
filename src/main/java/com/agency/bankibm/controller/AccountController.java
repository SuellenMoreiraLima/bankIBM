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

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/deposit/{id}")
    public ResponseEntity<AccountDTO> depositar(@PathVariable int id, @RequestBody double valorDeposito) {
        try {
            AccountDTO accountDTO = accountService.depositar(id, valorDeposito);
            return ResponseEntity.ok(accountDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/sacar/{id}")
    public ResponseEntity<AccountDTO> sacar(@PathVariable int id, @RequestBody double valorSaque) {
        try {
            AccountDTO accountDTO = accountService.sacar(id, valorSaque);
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
    public ResponseEntity<AccountDTO> debitar(@PathVariable int id, @RequestBody double valorDebito) {
//        double valorDebito = request.getValorDebito();
        System.out.println("Valor de débito recebido: " + valorDebito);
        try {
            // Chamar o método debitar do serviço passando o ID da conta e o valor do débito
            AccountDTO accountDTO = accountService.debito(id, valorDebito);
            return ResponseEntity.ok(accountDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/credit/{id}")
    public ResponseEntity<AccountDTO> creditar(@PathVariable int id, @RequestBody double valorCompraNoCredito) {
        try {
            AccountDTO accountDTO = accountService.credito(id, valorCompraNoCredito);
            return ResponseEntity.ok(accountDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<AccountDTO> saveAccount(@RequestBody AccountDTO accountDTO) {
    AccountDTO savedAccount = accountService.saveAccount(accountDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedAccount);
    }

//    @PostMapping("/transferir")
//    public ResponseEntity<String> transferir(@RequestParam int origemId,
//                                             @RequestParam int destinoId,
//                                             @RequestParam double valor) {
//        accountService.transferirPorNumeroConta(origemId, destinoId, valor);
//        return ResponseEntity.ok("Transferência realizada com sucesso!");
//    }

}
