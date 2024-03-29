package com.kangmin.app.model.payload.custom;

import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.NotBlank;

@Data
public class BuyFundRequest {

    @NotBlank
    private String symbol;

    @Pattern(regexp = "^\\d*\\.?\\d{0,2}$")
    @NotBlank
    private String cashValue;

    @Override
    public String toString() {
        return "BuyFundForm{"
                + "symbol='" + symbol + '\''
                + ", cashValue='" + cashValue + '\''
                + '}';
    }
}
