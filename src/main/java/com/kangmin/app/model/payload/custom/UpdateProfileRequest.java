package com.kangmin.app.model.payload.custom;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class UpdateProfileRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String name;
}
