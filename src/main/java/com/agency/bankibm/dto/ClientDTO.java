package com.agency.bankibm.dto;

import com.agency.bankibm.model.Account;
import com.agency.bankibm.model.Client;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

@Getter
@Setter
public class ClientDTO {
    private int id;
    private LocalDate dateNasciment;
    private String name;
    private String email;
    private String password;
    private String phone;
    private int numberAccount;
    private Account account;


    public Client toEntity(){
        ModelMapper mapper = new ModelMapper();

        return mapper.map(this, Client.class);
    }

}
