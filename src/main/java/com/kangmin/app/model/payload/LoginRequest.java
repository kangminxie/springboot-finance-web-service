package com.kangmin.app.model.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class LoginRequest {

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
