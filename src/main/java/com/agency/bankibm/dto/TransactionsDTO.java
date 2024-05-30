package com.agency.bankibm.dto;

import com.agency.bankibm.model.Transactions;
import com.agency.bankibm.model.TransactionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

public class TransactionsDTO {

    private int id;
    private double value;
    private String description;
    private TransactionType type;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;

    public TransactionsDTO() {
        this.dateTime = LocalDateTime.now(); // Define o dateTime como o horário atual ao criar uma instância de TransactionsDTO
    }

    public Transactions toEntity(){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(this, Transactions.class);
    }
}
