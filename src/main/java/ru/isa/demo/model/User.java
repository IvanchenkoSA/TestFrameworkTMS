package ru.isa.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private String username;
    private String password;
    private ROLE role;


    public enum ROLE {
        ADMIN,
        USER
    }

}
