package com.kangmin.app.model.payload.admin;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
public class DepositCheckRequest {

    @NotBlank
    private String username;

    @NotBlank
    @Digits(integer = 11, fraction = 2)
    @Min(value = 0)
    private String amount;

    @Override
    public String toString() {
        return "DepositForm{"
                + "username='" + username + '\'' + ", "
                + "amount='" + amount + '\''
                + '}';
    }
}
