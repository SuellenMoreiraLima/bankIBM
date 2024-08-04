package com.agency.bankibm.service;

import com.agency.bankibm.dto.ClientDTO;
import com.agency.bankibm.model.Account;
import com.agency.bankibm.model.Client;
import com.agency.bankibm.repository.AccountRepository;
import com.agency.bankibm.repository.ClientRepository;
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

    @Autowired
    public ClientService(ClientRepository clientRepository, AccountRepository accountRepository) {
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
    }

    public List<ClientDTO> getAll() {
        List<Client> lista = clientRepository.findAll();
        List<ClientDTO> listaDTO = new ArrayList<>();
        // Converte cada Client para ClientDTO e adiciona à lista
        for (Client usuarioEntity : lista) {
            listaDTO.add(usuarioEntity.toDTO());
        }

        return listaDTO;
    }

    public ClientDTO getOne(int idClient) {

        Optional<Client> optional = clientRepository.findById(idClient);
        Client client = optional.orElse(new Client());
        return client.toDTO();
    }

    @Transactional
    public ClientDTO saveClient(ClientDTO clientDTO) {
        // Cria uma nova conta e a salva no repositório
        Account account = new Account();
        accountRepository.save(account);

        // Converte o ClientDTO para Client e associa a conta criada
        Client client = clientDTO.toEntity();
        client.setAccount(account); // Associa a conta ao cliente
        clientRepository.save(client);

        return client.toDTO();
    }

    public ClientDTO updateClient(int idClient, ClientDTO clientDTO) {
        Optional<Client> optional = clientRepository.findById(idClient);

        // Se o cliente for encontrado, atualiza seus dados
        if (optional.isPresent()) {
            Client clientBd = optional.get();
            clientBd.setName(clientDTO.getName());
            clientBd.setDateNasciment(clientDTO.getDateNasciment());
            clientBd.setEmail(clientDTO.getEmail());
            clientBd.setNumberAccount(clientDTO.getNumberAccount());

            // Atualiza os dados da conta, se existirem
            if (clientBd.getAccount() != null && clientDTO.getAccount() != null) {
                clientBd.getAccount().setBalance(clientDTO.getAccount().getBalance());
                clientBd.getAccount().setTotalLimit(clientDTO.getAccount().getTotalLimit());
            }

            return clientRepository.save(clientBd).toDTO();
        } else {
            // Se o cliente não for encontrado, retorna um DTO vazio
            return new Client().toDTO();
        }
    }

    public void delete(int idClient) {
        clientRepository.deleteById(idClient);
    }
}
