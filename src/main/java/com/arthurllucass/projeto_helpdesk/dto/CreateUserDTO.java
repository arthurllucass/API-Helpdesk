package com.arthurllucass.projeto_helpdesk.dto;

import com.arthurllucass.projeto_helpdesk.model.enums.UserRole;

import java.io.Serializable;

public record CreateUserDTO(String name, String email, String phone, String password, UserRole role) implements Serializable {

    private static final long serialVersionUID = 1L;
}
