// Client.java
package com.agency.bankibm.model;

import com.agency.bankibm.dto.ClientDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate dateNasciment;
    private String name;
    private String email;
    private String password;
    private String phone;
    private int numberAccount;
//    private String address;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Account account;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateNasciment() {
        return dateNasciment;
    }

    public void setDateNasciment(LocalDate dateNasciment) {
        this.dateNasciment = dateNasciment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberAccount() {
        return numberAccount;
    }

    public void setNumberAccount(int numberAccount) {
        this.numberAccount = numberAccount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ClientDTO toDTO() {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(this, ClientDTO.class);
    }

}
