package com.agency.bankibm.controller;
import com.agency.bankibm.dto.ClientDTO;
import com.agency.bankibm.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/client")
public class ClientController {

    private final ClientService clientService;


    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping()
    public ResponseEntity<List<ClientDTO>> getAll() {
        // Chama o serviço para obter todos os clientes e retorna a resposta com status HTTP 200 (OK)
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getAll());
    }

    @GetMapping("{idClient}")
    public ResponseEntity<ClientDTO> getOne(@PathVariable int idClient) {
        // Chama o serviço para obter um cliente pelo ID e retorna a resposta com status HTTP 200 (OK)
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getOne(idClient));
    }

    @PostMapping
    public ResponseEntity<ClientDTO> saveClient(@RequestBody ClientDTO clientDTO) {
        // Chama o serviço para salvar o cliente e retorna a resposta com status HTTP 201 (CREATED)
        ClientDTO savedClient = clientService.saveClient(clientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable int id, @RequestBody ClientDTO clientDTO) {
        // Chama o serviço para atualizar o cliente e retorna a resposta com status HTTP 200 (OK) se o cliente foi encontrado
        ClientDTO updatedClient = clientService.updateClient(id, clientDTO);
        if (updatedClient != null) {
            return ResponseEntity.ok(updatedClient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{idClient}")
    public void delete(@PathVariable int idClient) {
        // Chama o serviço para deletar o cliente
        clientService.delete(idClient);
    }
}
