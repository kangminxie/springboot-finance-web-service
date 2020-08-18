package com.kangmin.app.util;

import com.kangmin.app.model.Account;
import com.kangmin.app.model.payload.admin.CreateAccountRequest;

import java.util.UUID;

public final class AccountUtil {

    public static Account mapFormToAccount(final CreateAccountRequest form) {
        return new Account(
                UUID.randomUUID().toString(),
                form.getEmail(),
                form.getUsername(),
                form.getName(),
                "NORMAL",
                form.getPassword(),
                Double.parseDouble(form.getCash())
        );
    }
}
