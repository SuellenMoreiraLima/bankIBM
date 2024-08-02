package com.agency.bankibm.controller;

// Importação das classes necessárias
import com.agency.bankibm.dto.AccountDTO;
import com.agency.bankibm.dto.ClientDTO;
import com.agency.bankibm.service.AccountService;
import com.agency.bankibm.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Permite que essa classe aceite requisições de outras origens (cross-origin)
@CrossOrigin(origins = "*")
// Indica que essa classe é um controlador REST
@RestController
// Define o caminho base para as requisições desse controlador
@RequestMapping(value = "/client")
public class ClientController {

    // Declaração do serviço de cliente que será usado pelo controlador
    private final ClientService clientService;

    // Injeção de dependência do serviço de cliente via construtor
    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    // Método para obter todos os clientes
    @GetMapping()
    public ResponseEntity<List<ClientDTO>> getAll() {
        // Chama o serviço para obter todos os clientes e retorna a resposta com status HTTP 200 (OK)
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getAll());
    }

    // Método para obter um cliente específico pelo ID
    @GetMapping("{idClient}")
    public ResponseEntity<ClientDTO> getOne(@PathVariable int idClient) {
        // Chama o serviço para obter um cliente pelo ID e retorna a resposta com status HTTP 200 (OK)
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getOne(idClient));
    }

    // Método para salvar um novo cliente
    @PostMapping
    public ResponseEntity<ClientDTO> saveClient(@RequestBody ClientDTO clientDTO) {
        // Chama o serviço para salvar o cliente e retorna a resposta com status HTTP 201 (CREATED)
        ClientDTO savedClient = clientService.saveClient(clientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
    }

    // Método para atualizar um cliente existente
    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable int id, @RequestBody ClientDTO clientDTO) {
        // Chama o serviço para atualizar o cliente e retorna a resposta com status HTTP 200 (OK) se o cliente foi encontrado
        ClientDTO updatedClient = clientService.updateClient(id, clientDTO);
        if (updatedClient != null) {
            return ResponseEntity.ok(updatedClient);
        } else {
            // Retorna uma resposta com status HTTP 404 (NOT FOUND) se o cliente não foi encontrado
            return ResponseEntity.notFound().build();
        }
    }

    // Método para deletar um cliente pelo ID
    @DeleteMapping("{idClient}")
    public void delete(@PathVariable int idClient) {
        // Chama o serviço para deletar o cliente
        clientService.delete(idClient);
    }
}
