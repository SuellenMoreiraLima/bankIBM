package com.agency.bankibm.dto;

import com.agency.bankibm.model.Account;
import com.agency.bankibm.model.Client;
import com.agency.bankibm.model.Login;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class ClientDTO {
    private int id;

    private int age;

    private String name;

    private int numberAccount;

    private Account account;

    private Login login;

    public Client toEntity(){
        ModelMapper mapper = new ModelMapper();

        return mapper.map(this, Client.class);
    }

}
