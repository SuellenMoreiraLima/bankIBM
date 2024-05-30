package com.agency.bankibm.service;

import com.agency.bankibm.dto.AccountDTO;
import com.agency.bankibm.model.Account;
import com.agency.bankibm.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
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
}
