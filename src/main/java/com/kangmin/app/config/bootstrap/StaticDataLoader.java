package com.kangmin.app.config.bootstrap;

import com.kangmin.app.model.Account;
import com.kangmin.app.service.AccountService;
import com.kangmin.app.service.FundService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Component
public class StaticDataLoader implements CommandLineRunner {

    private final AccountService accountService;
    private final FundService fundService;

    public StaticDataLoader(final AccountService accountService,
                            final FundService fundService) {
        this.accountService = accountService;
        this.fundService = fundService;
    }

    @Override
    public void run(final String... strings) throws ParseException {
        if (accountService.getAll().isEmpty()) {
            accountService.createAccount(
                    new Account(
                            "id-0000",
                            "dev@security.com",
                            "dev",
                            "Name-dev",
                            "SUPER_ADMIN",
                            "dev"
                    )
            );
            accountService.createAccount(
                    new Account(
                            "id-0001",
                            "sa@sa.com",
                            "sa",
                            "NameSA",
                            "SUPER_ADMIN",
                            "password"
                    )
            );
            accountService.createAccount(
                    new Account(
                            "id-0111",
                            "normal111@security.com",
                            "normal",
                            "NameNormal",
                            "NORMAL",
                            "normal",
                            5000.0
                    )
            );
            accountService.createAccount(
                    new Account(
                            "id-0222",
                            "admin222@security.com",
                            "admin",
                            "NameAdmin",
                            "ADMIN",
                            "admin"
                    )
            );
        }

        if (fundService.getAll().isEmpty()) {
            fundService.createFund("Amazon", "AMZN", 100.00);
            fundService.createFund("Apple", "APPL", 200.00);
            fundService.createFund("Facebook", "FABK", 150.00);
            fundService.createFund("Google", "GOOG", 300.00);
        }
    }
}
