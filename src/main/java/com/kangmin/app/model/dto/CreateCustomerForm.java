package com.kangmin.app.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class CreateCustomerForm {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String username;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    @NotBlank
    private String password2;

    @Digits(integer = 11, fraction = 2)
    @Min(value = 0)
    private String cash;

    @Override
    public String toString() {
        return "CreateCustomerForm{"
                + "email='" + email + '\''
                + "username='" + username + '\''
                + ", name='" + name + '\''
                + ", password='" + password + '\''
                + ", password2='" + password2 + '\''
                + ", cash='" + cash + '\''
                + '}';
    }
}
