package com.agency.bankibm.repository;

import com.agency.bankibm.model.Client;
import com.agency.bankibm.model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Integer> {
}
