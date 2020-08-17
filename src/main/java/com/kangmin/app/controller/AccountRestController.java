package com.kangmin.app.controller;

import com.kangmin.app.model.Account;
import com.kangmin.app.model.CustomResponse;
import com.kangmin.app.model.dto.LoginForm;
import com.kangmin.app.service.AccountService;

import com.kangmin.app.util.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.kangmin.app.util.AttributeName.SESSION_ACCOUNT;

@RestController
public class AccountRestController {

    private final AccountService accountService;

    public AccountRestController(final AccountService accountService) {
        this.accountService = accountService;
    }

    // == GET /login ==
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity<CustomResponse> getLogin() {
        final CustomResponse response = new CustomResponse();
        response.setMessage(Message.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // == POST /login ==
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<CustomResponse> login(final @Valid @RequestBody LoginForm form,
                                                final BindingResult bindingResult,
                                                final HttpServletRequest request) {
        HttpSession session = request.getSession();
        final CustomResponse response = new CustomResponse();

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        final Optional<Account> sessionAccount = accountService.loginAuthenticate(
                form.getUsername(),
                form.getPassword()
        );

        if (sessionAccount.isPresent()) {
            session.setAttribute(SESSION_ACCOUNT, sessionAccount.get());
            response.setMessage("Welcome " + sessionAccount.get().getUsername());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        response.setMessage(Message.INCORRECT_CREDENTIALS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // == POST /logout ==
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity<CustomResponse> logoutCurrentAccount(final HttpServletRequest request) {
        final HttpSession session = request.getSession();

        final Account sessionAccount = (Account) session.getAttribute(SESSION_ACCOUNT);
        final CustomResponse response = new CustomResponse();

        if (sessionAccount == null) {
            response.setMessage(Message.NOT_LOGGED_IN);
        } else {
            // == login user ==
            session.setAttribute(SESSION_ACCOUNT, null);
            response.setMessage(Message.LOGGED_OUT);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
