package com.agency.bankibm.service;

import com.agency.bankibm.dto.LoginRequestDTO;
import com.agency.bankibm.dto.LoginResponseDTO;
import com.agency.bankibm.model.Client;
import com.agency.bankibm.repository.ClientRepository;
import com.agency.bankibm.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final ClientRepository clientRepository;
    private final LoginRepository loginRepository;

    @Autowired
    public LoginService(ClientRepository clientRepository, LoginRepository loginRepository) {
        this.clientRepository = clientRepository;
        this.loginRepository = loginRepository;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        Client client = (Client) loginRepository.findByEmail(loginRequest.getEmail());
        if (client != null && client.getNumberAccount() == loginRequest.getAccountNumber()) {
            // Cliente encontrado e número da conta coincidente
            return new LoginResponseDTO("Login bem-sucedido!");
        } else {
            // Cliente não encontrado ou número da conta incorreto
            return new LoginResponseDTO("Credenciais inválidas. Por favor, verifique seu e-mail e número da conta.");
        }
    }
}
