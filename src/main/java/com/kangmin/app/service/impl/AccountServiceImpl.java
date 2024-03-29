package com.kangmin.app.service.impl;

import com.kangmin.app.dao.AccountDao;
import com.kangmin.app.dao.PositionDao;
import com.kangmin.app.model.Account;
import com.kangmin.app.model.response.CustomResponse;
import com.kangmin.app.model.Position;
import com.kangmin.app.model.viewbean.FundInfo;
import com.kangmin.app.service.AccountService;
import com.kangmin.app.util.AccountUtil;
import com.kangmin.app.util.Message;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountDao accountDao;
    private final PositionDao positionDao;

    public AccountServiceImpl(final AccountDao accountDao,
                              final PositionDao positionDao) {
        this.accountDao = accountDao;
        this.positionDao = positionDao;
    }

    @Override
    public List<Account> getAll() {
        return accountDao.findAll().stream()
                .peek(each -> each.setPassword("[***protected***]"))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Account> findByUsername(final String username) {
        return accountDao.findByUsername(username);
    }

    @Override
    public Optional<Account> findById(final String id) {
        return accountDao.findById(id);
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
        if (account.getId().isEmpty()) {
            account.setId(UUID.randomUUID().toString());
        }
        accountDao.save(account);
        return Optional.of(account);
    }

    @Override
    public Optional<Account> loginAuthenticate(final String username, final String password) {
        final Optional<Account> accountOpt = accountDao.findByUsername(username);
        if (accountOpt.isPresent()) {
            final Account targetAccount = accountOpt.get();
            if (!targetAccount.getPassword().equals(password)) {
                return Optional.empty();
            }

            targetAccount.setPassword("[***protected***]");
            return Optional.of(targetAccount);
        }
        return Optional.empty();
    }

    @Override
    public synchronized ResponseEntity<?> depositCheck(final String username, final String amountStr) {
        final CustomResponse response = new CustomResponse();
        final Optional<Account> accountOpt = accountDao.findByUsername(username);

        if (accountOpt.isPresent()) {
            final Account account = accountOpt.get();
            final double amount = Double.parseDouble(amountStr);
            account.setCash(account.getCash() + amount);
            accountDao.save(account);
            response.setMessage(Message.DEPOSIT_CHECK_SUCCESS);
        } else {
            response.setMessage(Message.ACCOUNT_DOES_NOT_EXIST);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public synchronized ResponseEntity<?> requestCheck(final String username, final String amountStr) {
        final CustomResponse response = new CustomResponse();
        final Optional<Account> accountOpt = accountDao.findByUsername(username);
        if (accountOpt.isPresent()) {
            final Account account = accountOpt.get();
            final double amount = Double.parseDouble(amountStr);
            final double newCash = account.getCash() - amount;

            if (newCash < 0) {
                response.setMessage(Message.INSUFFICIENT_CASH_CHECK_REQUEST);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            account.setCash(newCash);
            accountDao.save(account);
            response.setMessage(Message.REQUEST_CHECK_SUCCESS);
        } else {
            response.setMessage(Message.ACCOUNT_DOES_NOT_EXIST);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public synchronized ResponseEntity<?> viewPortfolio(final Account account) {
        final Map<String, Object> map = new HashMap<>();
        map.put("id", account.getId());
        map.put("role", account.getRole());
        map.put("username", account.getUsername());
        map.put("email", account.getEmail());
        map.put("name", account.getName());
        map.put("cash", account.getCash());

        final List<Position> positions = positionDao.findByAccount(account);
        if (positions.isEmpty()) {
            map.put("message", Message.NO_FUNDS_IN_PORTFOLIO);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }

        final List<FundInfo> list = new ArrayList<>();
        for (final Position p : positions) {
            final FundInfo fundInfo = mappingPositionToFundInfo(p);
            if (fundInfo.getShares() > 0) {
                list.add(fundInfo);
            }
        }
        if (list.size() == 0) {
            map.put("message", Message.NO_FUNDS_IN_PORTFOLIO);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }

        map.put("funds", list);
        map.put("message", "The action was successful");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @Override
    public boolean updateProfile(final String username,
                                 final String email,
                                 final String name) {
        final CustomResponse response = new CustomResponse();

        final Optional<Account> accountOpt = accountDao.findByUsername(username);
        if (accountOpt.isPresent()) {
            final Account account = accountOpt.get();
            account.setEmail(email);
            account.setName(name);
            accountDao.save(account);
            // == need to refresh session ==
            return true;
        }

        return false;
    }

    @Override
    public ResponseEntity<?> updatePassword(final String username,
                                            final String currentPassword,
                                            final String newPassword) {
        final CustomResponse response = new CustomResponse();
        final Optional<Account> accountOpt = accountDao.findByUsername(username);
        if (accountOpt.isEmpty()) {
            response.setMessage(Message.ACCOUNT_DOES_NOT_EXIST);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        final Account account = accountOpt.get();
        if (account.getPassword().equals(currentPassword)) {
            account.setPassword(newPassword);
            accountDao.save(account);
            response.setMessage(Message.PASSWORD_UPDATE_SUCCESS);
        } else {
            response.setMessage(Message.CURRENT_PASSWORD_INCORRECT);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> resetCustomPassword(final String username) {
        final CustomResponse response = new CustomResponse();
        final Optional<Account> accountOpt = accountDao.findByUsername(username);

        if (accountOpt.isPresent()) {
            final Account account = accountOpt.get();
            if (account.getRole().equals("NORMAL")) {
                final String newPassword = AccountUtil.generateRandomStr(8);
                account.setPassword(newPassword);
                accountDao.save(account);
                response.setMessage(String.format(
                        "Successfully generated new password for %s: %s.", username, newPassword
                ));
            } else {
                // == admin could not reset admins' password ==
                response.setMessage(Message.NOT_AUTHORIZED);
            }
        } else {
            response.setMessage(Message.ACCOUNT_DOES_NOT_EXIST);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private FundInfo mappingPositionToFundInfo(final Position p) {
        final FundInfo fundInfo = new FundInfo();
        fundInfo.setName(p.getFund().getName());
        fundInfo.setShares(p.getShares());
        fundInfo.setPrice(p.getFund().getPrice());
        return fundInfo;
    }
}
