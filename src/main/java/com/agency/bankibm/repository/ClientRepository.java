package com.agency.bankibm.repository;

import com.agency.bankibm.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
}
