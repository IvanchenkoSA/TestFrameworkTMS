package ru.isa.demo.dto;

import ru.isa.demo.model.User;

public record UserDTO(String username, String password, User.ROLE role) {
}
