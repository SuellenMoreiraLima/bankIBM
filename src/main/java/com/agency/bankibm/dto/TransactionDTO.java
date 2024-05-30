package com.agency.bankibm.dto;

import com.agency.bankibm.model.Transactions;
import com.agency.bankibm.model.TransactionType;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

public class TransactionDTO {

    private int id;
    private double value;
    private String description;
    private TransactionType type;
    private LocalDateTime dateTime;

    public Transactions toEntity(){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(this, Transactions.class);
    }
}
