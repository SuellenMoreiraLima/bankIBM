package com.agency.bankibm.dto;

import com.agency.bankibm.model.Account;
import com.agency.bankibm.model.Client;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class ClientDTO {
    private int id;

    private int age;

    private String name;

    private String email;

    private int numberAccount;

    private Account account;

    public Client toEntity(){
        ModelMapper mapper = new ModelMapper();

        return mapper.map(this, Client.class);
    }

}
