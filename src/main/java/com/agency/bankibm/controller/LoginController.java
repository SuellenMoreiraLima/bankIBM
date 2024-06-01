package com.agency.bankibm.controller;

import com.agency.bankibm.dto.LoginRequestDTO;
import com.agency.bankibm.dto.LoginResponseDTO;
import com.agency.bankibm.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequest) {
        return loginService.login(loginRequest);
    }
}
