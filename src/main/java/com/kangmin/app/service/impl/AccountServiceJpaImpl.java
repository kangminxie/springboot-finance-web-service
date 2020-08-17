package com.kangmin.app.service.impl;

import com.kangmin.app.dao.AccountDao;
import com.kangmin.app.model.Account;
import com.kangmin.app.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountServiceJpaImpl implements AccountService {

    private final AccountDao accountDao;

    public AccountServiceJpaImpl(final AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public List<Account> getAll() {
        return accountDao.findAll().stream()
                .peek(each -> each.setPassword("[***protected***]"))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountExistByEmailOrUsername(final String email, final String username) {
        return accountDao.existsAccountByEmailOrUsername(email, username);
    }

    @Override
    @Transactional
    public Optional<Account> createAccount(final Account account) {
        if (accountDao.findByUsername(account.getUsername()).isPresent()) {
            // already exist -> return empty: we did not create new account
            return Optional.empty();
        }
        // == new account ==
        if (account.getAccountId().isEmpty()) {
            account.setAccountId(UUID.randomUUID().toString());
        }
        accountDao.save(account);
        return Optional.of(account);
    }

    @Override
    public Optional<Account> loginAuthenticate(final String username, final String password) {
        final Optional<Account> accountOpt = accountDao.findByUsername(username);
        if (accountOpt.isPresent()) {
            final Account sessionAccount = accountOpt.get();
            sessionAccount.setPassword("[***protected***]");
            return Optional.of(sessionAccount);
        }
        return Optional.empty();
    }
}
