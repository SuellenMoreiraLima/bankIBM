// ClientService.java
package com.agency.bankibm.service;

import com.agency.bankibm.dto.ClientDTO;
import com.agency.bankibm.dto.LoginDTO;
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
        // Criar uma nova conta
        Account account = new Account();
        accountRepository.save(account); // Salva a conta no banco de dados

        // Criar um novo cliente
        Client client = clientDTO.toEntity(); // Converte o DTO para entidade
        client.setAccount(account); // Associa a conta ao cliente
        clientRepository.save(client); // Salva o cliente no banco de dados

        if (clientDTO.getLogin() != null) {

            Login login = new Login();
            login.setEmail(clientDTO.getLogin().getEmail());
            login.setPassword(clientDTO.getLogin().getPassword());
            loginRepository.save(login);
        }

//        // Criar um novo login
//        Login login = new Login();
//        login.setEmail(clientDTO.getLogin().getEmail());
//        login.setPassword(clientDTO.getLogin().getPassword());
//        loginRepository.save(login);


        return client.toDTO(); // Retorna o DTO do cliente criado
    }


//    public ClientDTO saveClient(ClientDTO clientDTO) {
//        // Criar uma nova conta
//        Account account = new Account();
//        account.setBalance(clientDTO.getAccount().getBalance()); // Define o saldo inicial
//        account.setTotalLimit(clientDTO.getAccount().getTotalLimit()); // Define o limite total inicial
//        accountRepository.save(account); // Salva a conta no banco de dados
//
//        // Criar um novo cliente
//        Client client = clientDTO.toEntity(); // Converte o DTO para entidade
//        client.setAccount(account); // Associa a conta ao cliente
//        clientRepository.save(client); // Salva o cliente no banco de dados
//
//        // Criar um novo login
//        Login login = new Login();
//        login.setClient(client);
//        login.setEmail(client.getEmail());
//        login.getClient().setNumberAccount(client.getNumberAccount());
//        login.setPassword(password); // Usa a senha fornecida
//        loginRepository.save(login); // Salva o login no banco de dados
//
//        return client.toDTO(); // Retorna o DTO do cliente criado
//    }


    public ClientDTO updateClient(int idClient, ClientDTO clientDTO) {
        Optional<Client> optional = clientRepository.findById(idClient);

        if(optional.isPresent() == true ){
            Client clientBd = optional.get();
            clientBd.setName(clientDTO.getName());
//            clientBd.setEmail(clientDTO.getEmail());
            clientBd.setAge(clientDTO.getAge());
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
