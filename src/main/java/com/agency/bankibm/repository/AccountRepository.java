package com.agency.bankibm.repository;

import com.agency.bankibm.model.Account;
import com.agency.bankibm.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
//    Optional<Client> findByNumberAccount(int numberAccount);
}
