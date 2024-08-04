package com.agency.bankibm.dto;

import com.agency.bankibm.model.Account;
import com.agency.bankibm.model.Status;
import com.agency.bankibm.model.Transactions;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.List;

@Getter
@Setter
public class AccountDTO {

    private int id;
    private double balance;
    private double totalLimit;
    private double availableLimit;
    private double usedLimit;
    private Status status;
    private List<Transactions> transactions;

    public Account toEntity(){
        ModelMapper mapper = new ModelMapper();

        return mapper.map(this, Account.class);
    }


}
