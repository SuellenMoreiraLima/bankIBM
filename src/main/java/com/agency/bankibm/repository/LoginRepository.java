package com.agency.bankibm.repository;

import com.agency.bankibm.model.Login;
import com.agency.bankibm.model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoginRepository extends JpaRepository<Login, Integer> {
    List<Transactions> findByEmail(String email);
}
