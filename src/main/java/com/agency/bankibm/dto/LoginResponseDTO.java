package com.agency.bankibm.dto;

public class LoginResponseDTO {
    private String message;

    public LoginResponseDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
