package com.kangmin.app.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class LoginForm {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @Override
    public String toString() {
        return "LoginForm{"
                + "username='" + username + '\'' + ", "
                + "password='" + password + '\''
                + '}';
    }
}
