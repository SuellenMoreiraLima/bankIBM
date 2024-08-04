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

        Optional<Account> getAccount = accountRepository.findById(accountId);
        if (!getAccount.isPresent()) {
            throw new RuntimeException("Conta não encontrada com ID: " + accountId);
        }

        // Atualizar o saldo da conta
        Account account = getAccount.get();
        account.setBalance(account.getBalance() + valorDeposito);
        Account savedAccount = accountRepository.save(account);

        // Registrar a transação de depósito
        Transactions depositTransaction = new Transactions();
        depositTransaction.setValueDescription(valorDeposito);
        depositTransaction.setDescription("Depósito");
        depositTransaction.setType(TransactionType.DEPOSIT);
        depositTransaction.setDateTime(LocalDateTime.now());
        depositTransaction.setAccount(savedAccount);
        transactionsRepository.save(depositTransaction);

        return savedAccount.toDTO();
    }

    @Transactional
    public AccountDTO sacar(int accountId, double valorSaque) {
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
        Account savedAccount = accountRepository.save(account);

        // Registrar a transação de saque
        Transactions transaction = new Transactions();
        transaction.setValueDescription(valorSaque);
        transaction.setDescription("Saque");
        transaction.setType(TransactionType.WITHDRAW);
        transaction.setDateTime(LocalDateTime.now());
        transaction.setAccount(savedAccount);
        transactionsRepository.save(transaction);

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

        return savedAccount.toDTO();
    }

    @Transactional
    public AccountDTO credito(int accountId, double valorCompra) {
        // Obter a conta com base no ID
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (!optionalAccount.isPresent()) {
            throw new RuntimeException("Conta não encontrada com ID: " + accountId);
        }

        // Verificar se o limite disponível é suficiente para a compra
        Account account = optionalAccount.get();

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

        return savedAccount.toDTO();
    }

    public AccountDTO saveAccount(AccountDTO dto) {
        Account account = new Account(dto);
        return account.toDTO();
    }

    @Transactional(readOnly = true)
    public double getAccountBalance(int accountId) {
        // Obter a conta com base no ID
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (!optionalAccount.isPresent()) {
            throw new RuntimeException("Conta não encontrada com ID: " + accountId);
        }
        return optionalAccount.get().getBalance();
    }

    private void updateBalance(Account account, double newBalance) {
        // Lógica de validação e atualização do saldo
        if (newBalance < 0) {
            throw new RuntimeException("Saldo não pode ser negativo");
        }
        account.setBalance(newBalance);
    }
}
