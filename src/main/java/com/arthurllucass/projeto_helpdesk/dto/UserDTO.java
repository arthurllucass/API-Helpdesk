package com.arthurllucass.projeto_helpdesk.dto;

import com.arthurllucass.projeto_helpdesk.model.entites.User;
import com.arthurllucass.projeto_helpdesk.model.enums.UserRole;

import java.io.Serializable;
import java.time.LocalDateTime;

public record UserDTO(Long id, String name, String email, String phone, UserRole role, LocalDateTime creationDate) implements Serializable {

    private static final long serialVersionUID = 1L;

    public UserDTO(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getPhone(), user.getRole(), user.getCreationDate());
    }
}