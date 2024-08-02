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

    // Declaração dos repositórios usados pelo serviço
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;

    // Construtor que inicializa os repositórios com injeção de dependência
    @Autowired
    public ClientService(ClientRepository clientRepository, AccountRepository accountRepository) {
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
    }

    // Método que retorna todos os clientes como uma lista de ClientDTO
    public List<ClientDTO> getAll() {
        // Busca todos os clientes no repositório
        List<Client> lista = clientRepository.findAll();

        // Cria uma lista de ClientDTO
        List<ClientDTO> listaDTO = new ArrayList<>();
        // Converte cada Client para ClientDTO e adiciona à lista
        for (Client usuarioEntity : lista) {
            listaDTO.add(usuarioEntity.toDTO());
        }

        // Retorna a lista de ClientDTO
        return listaDTO;
    }

    // Método que retorna um cliente específico com base no ID
    public ClientDTO getOne(int idClient) {
        // Busca o cliente pelo ID no repositório
        Optional<Client> optional = clientRepository.findById(idClient);

        // Se o cliente não for encontrado, cria um novo objeto Client vazio
        Client professor = optional.orElse(new Client());

        // Converte o cliente para ClientDTO e retorna
        return professor.toDTO();
    }

    // Método para salvar um novo cliente
    @Transactional
    public ClientDTO saveClient(ClientDTO clientDTO) {
        // Cria uma nova conta e a salva no repositório
        Account account = new Account();
        accountRepository.save(account);

        // Converte o ClientDTO para Client e associa a conta criada
        Client client = clientDTO.toEntity();
        client.setAccount(account); // Associa a conta ao cliente
        clientRepository.save(client);


        // Retorna o DTO do cliente criado
        return client.toDTO();
    }

    // Método para atualizar um cliente existente
    public ClientDTO updateClient(int idClient, ClientDTO clientDTO) {
        // Busca o cliente pelo ID no repositório
        Optional<Client> optional = clientRepository.findById(idClient);

        // Se o cliente for encontrado, atualiza seus dados
        if (optional.isPresent()) {
            Client clientBd = optional.get();
            clientBd.setName(clientDTO.getName());
            clientBd.setAge(clientDTO.getAge());
            clientBd.setEmail(clientDTO.getEmail());
            clientBd.setNumberAccount(clientDTO.getNumberAccount());

            // Atualiza os dados da conta, se existirem
            if (clientBd.getAccount() != null && clientDTO.getAccount() != null) {
                clientBd.getAccount().setBalance(clientDTO.getAccount().getBalance());
                clientBd.getAccount().setTotalLimit(clientDTO.getAccount().getTotalLimit());
            }

            // Salva o cliente atualizado e retorna seu DTO
            return clientRepository.save(clientBd).toDTO();
        } else {
            // Se o cliente não for encontrado, retorna um DTO vazio
            return new Client().toDTO();
        }
    }

    // Método para deletar um cliente pelo ID
    public void delete(int idClient) {
        // Deleta o cliente pelo ID no repositório
        clientRepository.deleteById(idClient);
    }
}
