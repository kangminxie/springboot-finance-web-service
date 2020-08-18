package com.kangmin.app.controller.rest;

import com.kangmin.app.model.Account;
import com.kangmin.app.model.response.CustomResponse;
import com.kangmin.app.model.payload.admin.CreateAccountRequest;
import com.kangmin.app.model.payload.admin.CreateFundRequest;
import com.kangmin.app.model.payload.admin.DepositCheckRequest;
import com.kangmin.app.model.payload.admin.ResetPasswordRequest;
import com.kangmin.app.model.payload.admin.TransitionDayRequest;
import com.kangmin.app.service.AccountService;
import com.kangmin.app.service.FundService;
import com.kangmin.app.service.TransitionDayService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AccountService accountService;
    private final FundService fundService;
    private final TransitionDayService transitionDayService;

    public AdminController(final AccountService accountService,
                           final FundService fundService,
                           final TransitionDayService transitionDayService) {
        this.accountService = accountService;
        this.fundService = fundService;
        this.transitionDayService = transitionDayService;
    }

    // == only admin can see all accounts ==
    @RequestMapping(value = {"/accounts"}, method = RequestMethod.GET)
    public ResponseEntity<List<Account>> getAllAccounts() {
        final List<Account> accounts = accountService.getAll();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    // == only admin can POST create new account ==
    @RequestMapping(value = "/accounts", method = RequestMethod.POST)
    public ResponseEntity<CustomResponse> registerNewAccount(
            final @Valid @RequestBody CreateAccountRequest form,
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
    public ResponseEntity<?> createNewFund(
            final @Valid @RequestBody CreateFundRequest form,
            final BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return fundService.createFund(
                form.getName(),
                form.getSymbol(),
                Double.parseDouble(form.getInitialPrice())
        );
    }

    // == only admin can POST deposit check for normal users ==
    @RequestMapping(value = {"/depositCheck"}, method = RequestMethod.POST)
    public ResponseEntity<?> depositCheck(
            final @Valid @RequestBody DepositCheckRequest form,
            final BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return accountService.depositCheck(form.getUsername(), form.getAmount());
    }

    @RequestMapping(value = "/transitionDay", method = RequestMethod.POST)
    public ResponseEntity<?> transitionDay(
            final @Valid @RequestBody(required = false) TransitionDayRequest form,
            final BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        final Map<String, String> changes = transitionDayService.transitionDay(form);
        final Map<String, Object> response = new HashMap<>();
        if (changes.isEmpty()) {
            response.put("message", "There is no recent changes for all funds");
        } else {
            response.put("message", Message.TRANSITION_DAY_SUCCESS);
            response.put("fluctuation", changes);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/reset-custom-password", method = RequestMethod.POST)
    public ResponseEntity<?> resetCustomPassword(
            final @Valid @RequestBody ResetPasswordRequest form,
            final BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        final String username = form.getUsername();
        return accountService.resetCustomPassword(username);
    }
}
