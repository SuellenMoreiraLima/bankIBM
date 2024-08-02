package com.agency.bankibm.dto;


import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
    public class LoginDTO {

        private int id;
        private String email;
        private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

    public Login toEntity(){
        ModelMapper mapper = new ModelMapper();

        return mapper.map(this, Login.class);
    }

}
