// ClientService.java
package com.agency.bankibm.service;

import com.agency.bankibm.dto.ClientDTO;
import com.agency.bankibm.model.Account;
import com.agency.bankibm.model.Client;
import com.agency.bankibm.model.Login;
import com.agency.bankibm.repository.AccountRepository;
import com.agency.bankibm.repository.ClientRepository;
import com.agency.bankibm.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClientService {

    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final LoginRepository loginRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository, AccountRepository accountRepository, LoginRepository loginRepository) {
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
        this.loginRepository = loginRepository;
    }

    public List<ClientDTO> getAll() {
        List<Client> lista =  clientRepository.findAll();

        List<ClientDTO> listaDTO = new ArrayList<>();
        for (Client usuarioEntity : lista) {
            listaDTO.add(usuarioEntity.toDTO() );
        }

        return listaDTO;
    }

    public ClientDTO getOne(int idClient) {

        Optional<Client> optional = clientRepository.findById(idClient);

        Client professor = optional.orElse( new Client() );

        return professor.toDTO();
    }

    @Transactional
    public ClientDTO saveClient(ClientDTO clientDTO) {

        Account account = new Account();
        accountRepository.save(account);

        Client client = clientDTO.toEntity();
        client.setAccount(account); // Associa a conta ao cliente
        clientRepository.save(client);

        if (clientDTO.getLogin() != null) {

            Login login = new Login();
            login.setEmail(clientDTO.getLogin().getEmail());
            login.setPassword(clientDTO.getLogin().getPassword());
            loginRepository.save(login);
        }


        return client.toDTO(); // Retorna o DTO do cliente criado
    }


    public ClientDTO updateClient(int idClient, ClientDTO clientDTO) {
        Optional<Client> optional = clientRepository.findById(idClient);

        if(optional.isPresent() == true ){
            Client clientBd = optional.get();
            clientBd.setName(clientDTO.getName());
            clientBd.setAge(clientDTO.getAge());
            clientBd.setEmail(clientDTO.getEmail());
            clientBd.setNumberAccount(clientDTO.getNumberAccount());

           if (clientBd.getAccount() != null && clientDTO.getAccount() != null) {
               clientBd.getAccount().setBalance(clientDTO.getAccount().getBalance());
               clientBd.getAccount().setTotalLimit(clientDTO.getAccount().getTotalLimit());
           }

            return clientRepository.save(clientBd).toDTO();
        }
        else {
            return new Client().toDTO();
        }
    }

    public void delete(int idClient) {
        clientRepository.deleteById(idClient);
    }
}
