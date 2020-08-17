package com.kangmin.app.controller;

import com.kangmin.app.model.Account;
import com.kangmin.app.model.CustomResponse;
import com.kangmin.app.model.Fund;
import com.kangmin.app.model.dto.CreateCustomerForm;
import com.kangmin.app.model.dto.CreateFundForm;
import com.kangmin.app.service.AccountService;
import com.kangmin.app.service.FundService;
import com.kangmin.app.util.AccountUtil;
import com.kangmin.app.util.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminRestController {

    private final AccountService accountService;
    private final FundService fundService;

    public AdminRestController(final AccountService accountService,
                               final FundService fundService) {
        this.accountService = accountService;
        this.fundService = fundService;
    }

    // == only admin can see all accounts ==
    @RequestMapping(value = {"/accounts"}, method = RequestMethod.GET)
    public ResponseEntity<List<Account>> getAllAccounts() {
        final List<Account> accounts = accountService.getAll();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    // == POST /register ==
    @RequestMapping(value = "/accounts", method = RequestMethod.POST)
    public ResponseEntity<CustomResponse> registerNewAccount(
            final @Valid @RequestBody CreateCustomerForm form,
            final BindingResult bindingResult
    ) {
        final CustomResponse response = new CustomResponse();

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!form.getPassword().equals(form.getPassword2())) {
            response.setMessage(Message.ACCOUNT_PASSWORD_NOT_MATCH);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        if (accountService.isAccountExistByEmailOrUsername(form.getEmail(), form.getUsername())) {
            response.setMessage(Message.ACCOUNT_ALREADY_EXIST);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        final Account account = AccountUtil.mapFormToAccount(form);
        accountService.createAccount(account);
        response.setMessage(Message.ACCOUNT_CREATED_SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // == only admin can POST new funds ==
    @RequestMapping(value = {"/funds"}, method = RequestMethod.POST)
    public ResponseEntity<CustomResponse> createNewFund(
            final @Valid @RequestBody CreateFundForm form,
            final BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        final CustomResponse response = new CustomResponse();
        final Optional<Fund> createdOpt = fundService.createFund(
                form.getName(),
                form.getSymbol(),
                Double.parseDouble(form.getInitialPrice())
        );

        if (createdOpt.isPresent()) {
            response.setMessage(Message.FUND_CREATED_SUCCESS);
        } else {
            response.setMessage(Message.FUND_ALREADY_EXIST);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
