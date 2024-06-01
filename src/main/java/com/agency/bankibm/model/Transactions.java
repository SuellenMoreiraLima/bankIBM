package com.agency.bankibm.model;

import com.agency.bankibm.dto.TransactionsDTO;
import jakarta.persistence.*;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transactions")
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double valueDescription;
    private String description;
    private TransactionType type;
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValueDescription() {
        return valueDescription;
    }

    public void setValueDescription(double valueDescription) {
        this.valueDescription = valueDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public TransactionsDTO toDTO() {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(this, TransactionsDTO.class);
    }
}
