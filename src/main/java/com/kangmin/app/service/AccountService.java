package com.kangmin.app.service;

import com.kangmin.app.model.Account;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    List<Account> getAll();

    Optional<Account> findByUsername(String username);

    Optional<Account> findById(String id);

    Optional<Account> createAccount(Account account);

    boolean isAccountExistByEmailOrUsername(String email, String username);

    Optional<Account> loginAuthenticate(String username, String password);

    ResponseEntity<?> depositCheck(String username, String amountStr);

    ResponseEntity<?> requestCheck(String username, String amountStr);

    ResponseEntity<?> viewPortfolio(Account account);

    boolean updateProfile(String username, String email, String name);

    ResponseEntity<?> updatePassword(String username, String currentPassword, String newPassword);

    ResponseEntity<?> resetCustomPassword(String username);
}
