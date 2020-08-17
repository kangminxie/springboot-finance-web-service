package com.kangmin.app.service;

import com.kangmin.app.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    List<Account> getAll();

    boolean isAccountExistByEmailOrUsername(String email, String username);

    Optional<Account> createAccount(Account account);

    Optional<Account> loginAuthenticate(String username, String password);
}
