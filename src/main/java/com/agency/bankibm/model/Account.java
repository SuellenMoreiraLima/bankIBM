// Account.java
package com.agency.bankibm.model;

import com.agency.bankibm.dto.AccountDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double balance;
    private double totalLimit;
    private double availableLimit;
    private double usedLimit;
    private Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getTotalLimit() {
        return totalLimit;
    }

    public void setTotalLimit(double totalLimit) {
        this.totalLimit = totalLimit;
    }

    public double getAvailableLimit() {
        return availableLimit;
    }

    public void setAvailableLimit(double availableLimit) {
        this.availableLimit = availableLimit;
    }

    public double getUsedLimit() {
        return usedLimit;
    }

    public void setUsedLimit(double usedLimit) {
        this.usedLimit = usedLimit;
    }


    public Account(AccountDTO dto) {
    }

    public AccountDTO toDTO() {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(this, AccountDTO.class);
    }
}
