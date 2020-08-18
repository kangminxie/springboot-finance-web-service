package com.kangmin.app.model.payload;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class SellFundRequest {

    @NotBlank
    private String symbol;

    @NotBlank
    @Pattern(regexp = "^\\d+$")
    @Min(value = 1)
    private String numShares;

    @Override
    public String toString() {
        return "SellFundForm{"
                + "symbol='" + symbol + '\'' + ", "
                + "numShares='" + numShares + '\''
                + '}';
    }
}
