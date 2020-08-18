package com.kangmin.app.model.payload.admin;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class CreateFundRequest {

    @NotBlank
    @Size(min = 1)
    private String name;

    @NotBlank
    @Size(min = 1)
    private String symbol;

    @NotBlank
    @Digits(integer = 11, fraction = 2)
    @Min(value = 0)
    private String initialPrice;

    @Override
    public String toString() {
        return "CreateFundForm{"
                + "name='" + name + '\'' + ", "
                + "symbol='" + symbol + '\'' + ", "
                + "initialPrice='" + initialPrice + '\''
                + '}';
    }
}
