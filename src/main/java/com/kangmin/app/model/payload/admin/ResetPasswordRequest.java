package com.kangmin.app.model.payload.admin;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class ResetPasswordRequest {

    @NotBlank
    private String username;
}
