package com.kangmin.app.config.bootstrap;

import com.kangmin.app.model.Account;
import com.kangmin.app.service.AccountService;
import com.kangmin.app.service.FundService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StaticDataLoader implements CommandLineRunner {

    private final AccountService accountService;
    private final FundService fundService;

    public StaticDataLoader(
        final AccountService accountService,
        final FundService fundService
    ) {
        this.accountService = accountService;
        this.fundService = fundService;
    }

    @Override
    public void run(final String... strings) {
        createInitialAccounts();
        createInitialFunds();
    }

    private void createInitialAccounts() {
        if (accountService.getAll().isEmpty()) {
            accountService.createAccount(
                new Account(
                    "id-0000",
                    "dev@security.com",
                    "dev",
                    "NameDev",
                    "SUPER_ADMIN",
                    "dev"
                )
            );
            accountService.createAccount(
                new Account(
                    "id-0001",
                    "sa@sa.com",
                    "sa",
                    "NameSuperAdmin",
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
                    12000.0
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
    }

    private void createInitialFunds() {
        if (fundService.getAll().isEmpty()) {
            fundService.createFund("Amazon", "AMZN", 3417.09);
            fundService.createFund("Apple", "AAPL", 176.32);
            fundService.createFund("Facebook", "FB", 335.62);
            fundService.createFund("Google", "GOOGL", 2938);
        }
    }
}
