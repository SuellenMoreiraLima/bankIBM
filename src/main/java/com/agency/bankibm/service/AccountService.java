package com.agency.bankibm.service;

import com.agency.bankibm.dto.AccountDTO;
import com.agency.bankibm.model.Account;
import com.agency.bankibm.model.TransactionType;
import com.agency.bankibm.model.Transactions;
import com.agency.bankibm.repository.AccountRepository;
import com.agency.bankibm.repository.TransactionsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionsRepository transactionsRepository;

    public AccountService(AccountRepository accountRepository, TransactionsRepository transactionsRepository) {
        this.accountRepository = accountRepository;
        this.transactionsRepository = transactionsRepository;
    }

    @Transactional
    public AccountDTO depositar(int accountId, double valorDeposito) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (!optionalAccount.isPresent()) {
            throw new RuntimeException("Conta não encontrada com ID: " + accountId);
        }

        Account account = optionalAccount.get();
        account.setBalance(account.getBalance() + valorDeposito);

        // Salvar a conta atualizada
        Account savedAccount = accountRepository.save(account);

        // Registrar a transação de depósito
        Transactions depositTransaction = new Transactions();
        depositTransaction.setValueDescription(valorDeposito);
        depositTransaction.setDescription("Depósito");
        depositTransaction.setType(TransactionType.DEPOSIT);
        depositTransaction.setDateTime(LocalDateTime.now());
        depositTransaction.setAccount(savedAccount);
        transactionsRepository.save(depositTransaction);

        // Retornar o DTO da conta atualizada
        return savedAccount.toDTO();
    }

    @Transactional
    public AccountDTO sacar(int accountId, double valorSaque) {
        // Obter a conta com base no ID
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (!optionalAccount.isPresent()) {
            throw new RuntimeException("Conta não encontrada com ID: " + accountId);
        }

        // Verificar se o saldo é suficiente para o saque
        Account account = optionalAccount.get();
        if (account.getBalance() < valorSaque) {
            throw new RuntimeException("Saldo insuficiente para sacar: " + valorSaque);
        }

        // Atualizar o saldo da conta
        account.setBalance(account.getBalance() - valorSaque);

        // Salvar a conta atualizada
        Account savedAccount = accountRepository.save(account);

        // Registrar a transação de saque
        Transactions withdrawTransaction = new Transactions();
        withdrawTransaction.setValueDescription(valorSaque);
        withdrawTransaction.setDescription("Saque");
        withdrawTransaction.setType(TransactionType.WITHDRAW);
        withdrawTransaction.setDateTime(LocalDateTime.now());
        withdrawTransaction.setAccount(savedAccount);
        transactionsRepository.save(withdrawTransaction);

        // Retornar o DTO da conta atualizada
        return savedAccount.toDTO();
    }

    @Transactional
    public AccountDTO debito(int accountId, double valorCompra) {
        // Obter a conta com base no ID
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (!optionalAccount.isPresent()) {
            throw new RuntimeException("Conta não encontrada com ID: " + accountId);
        }

        // Verificar se o saldo é suficiente para a compra
        Account account = optionalAccount.get();
        if (account.getBalance() < valorCompra) {
            throw new RuntimeException("Saldo insuficiente para realizar a compra: " + valorCompra);
        }

        // Atualizar o saldo da conta
        Account account1 = optionalAccount.get();
        account1.setBalance(account1.getBalance() - valorCompra);

        // Salvar a conta atualizada
        Account savedAccount = accountRepository.save(account);

        // Registrar a transação de débito
        Transactions debitTransaction = new Transactions();
        debitTransaction.setValueDescription(valorCompra);
        debitTransaction.setDescription("Compra no Débito");
        debitTransaction.setType(TransactionType.DEBIT);
        debitTransaction.setDateTime(LocalDateTime.now());
        debitTransaction.setAccount(savedAccount);
        transactionsRepository.save(debitTransaction);

        // Retornar o DTO da conta atualizada
        return savedAccount.toDTO();
    }

//    @Transactional
//    public AccountDTO credito(int accountId, double valorCompra) {
//        // Obter a conta com base no ID
//        Optional<Account> optionalAccount = accountRepository.findById(accountId);
//        if (!optionalAccount.isPresent()) {
//            throw new RuntimeException("Conta não encontrada com ID: " + accountId);
//        }
//
//        // Verificar se o limite disponível é suficiente para a compra
//        Account account = optionalAccount.get();
//        if (account.getAvailableLimit() < valorCompra) {
//            throw new RuntimeException("Limite disponível insuficiente para realizar a compra a crédito: " + valorCompra);
//        }
//
//        // Atualizar o limite disponível da conta
//        account.setAvailableLimit(account.getAvailableLimit() - valorCompra);
//
//        // Salvar a conta atualizada
//        Account savedAccount = accountRepository.save(account);
//
//        // Registrar a transação de crédito
//        Transactions creditTransaction = new Transactions();
//        creditTransaction.setValueDescription(valorCompra);
//        creditTransaction.setDescription("Compra a Crédito");
//        creditTransaction.setType(TransactionType.CREDIT);
//        creditTransaction.setDateTime(LocalDateTime.now());
//        creditTransaction.setAccount(savedAccount);
//        transactionsRepository.save(creditTransaction);
//
//        // Retornar o DTO da conta atualizada
//        return savedAccount.toDTO();
//    }

    @Transactional
    public AccountDTO credito(int accountId, double valorCompra) {
        // Obter a conta com base no ID
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (!optionalAccount.isPresent()) {
            throw new RuntimeException("Conta não encontrada com ID: " + accountId);
        }

        // Verificar se o limite disponível é suficiente para a compra
        Account account = optionalAccount.get();
//        double creditTotal = transactionsRepository
//                .findByAccountIdAndType(accountId, TransactionType.CREDIT)
//                .stream()
//                .mapToDouble(transaction -> transaction.getValueDescription()) // Usando uma expressão lambda
//                .sum();

        double creditTotal = transactionsRepository
                .findByAccountIdAndType(accountId, TransactionType.CREDIT)
                .stream()
                .map(Transactions.class::cast) // Convertendo para Transactions
                .mapToDouble(Transactions::getValueDescription) // Agora é seguro chamar getValueDescription()
                .sum();


        double availableLimit = account.getTotalLimit() - creditTotal;

        if (availableLimit < valorCompra) {
            throw new RuntimeException("Limite disponível insuficiente para realizar a compra a crédito: " + valorCompra);
        }

        // Atualizar o limite disponível da conta
        account.setAvailableLimit(availableLimit - valorCompra);

        // Salvar a conta atualizada
        Account savedAccount = accountRepository.save(account);

        // Registrar a transação de crédito
        Transactions creditTransaction = new Transactions();
        creditTransaction.setValueDescription(valorCompra);
        creditTransaction.setDescription("Compra a Crédito");
        creditTransaction.setType(TransactionType.CREDIT);
        creditTransaction.setDateTime(LocalDateTime.now());
        creditTransaction.setAccount(savedAccount);
        transactionsRepository.save(creditTransaction);

        // Retornar o DTO da conta atualizada
        return savedAccount.toDTO();
    }



    public AccountDTO saveAccount(AccountDTO dto) {
        Account account = new Account(dto);

//        Account account = new Account();
//        account.setBalance(dto.getBalance());
//        account.setCliente(dto.getCliente());
//        account.setTotalLimit(dto.getTotalLimit());
//        // Mapear os dados do DTO para a entidade Account
//        // Exemplo: account.setSaldoEmConta(accountDTO.getSaldoEmConta());
//        // Salvar a instância de Account
//        Account savedAccount = accountRepository.save(account);
//
//        // Converter o Account salvo de volta para AccountDTO
        return account.toDTO();
//
    }
    @Transactional(readOnly = true)
    public double getAccountBalance(int accountId) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (!optionalAccount.isPresent()) {
            throw new RuntimeException("Conta não encontrada com ID: " + accountId);
        }
        return optionalAccount.get().getBalance();
    }
}
