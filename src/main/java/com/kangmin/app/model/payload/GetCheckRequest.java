package com.kangmin.app.model.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class GetCheckRequest {

    @NotBlank
    @Digits(integer = 11, fraction = 2)
    @Min(value = 0)
    private String cashValue;

    @Override
    public String toString() {
        return "RequestForm{"
                + "cashValue='" + cashValue + '\''
                + '}';
    }
}
