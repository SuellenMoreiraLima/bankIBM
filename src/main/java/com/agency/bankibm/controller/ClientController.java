package com.agency.bankibm.controller;

import com.agency.bankibm.dto.AccountDTO;
import com.agency.bankibm.dto.ClientDTO;
import com.agency.bankibm.service.AccountService;
import com.agency.bankibm.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/client")
public class ClientController {

    private final ClientService clientService;

    @Autowired // Adiciona esta anotação para injetar o AccountService
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping()
    public ResponseEntity<List<ClientDTO>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getAll());
    }
    @GetMapping("{idClient}")
    public ResponseEntity<ClientDTO> getOne(@PathVariable int idClient) {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getOne(idClient));
    }

    @PostMapping
    public ResponseEntity<ClientDTO> saveClient(@RequestBody ClientDTO clientDTO) {
        ClientDTO savedAccount = clientService.saveClient(clientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAccount);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable int id, @RequestBody ClientDTO clientDTO) {
        ClientDTO updatedClient = clientService.updateClient(id, clientDTO);
        if (updatedClient != null) {
            return ResponseEntity.ok(updatedClient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{idClient}")
    public void delete(@PathVariable int idClient) {
        clientService.delete(idClient);
    }
}
