package com.kangmin.app.controller.rest;

import com.kangmin.app.model.Account;
import com.kangmin.app.model.response.CustomResponse;
import com.kangmin.app.model.payload.LoginRequest;
import com.kangmin.app.model.payload.custom.GetCheckRequest;
import com.kangmin.app.model.payload.custom.UpdatePasswordRequest;
import com.kangmin.app.model.payload.custom.UpdateProfileRequest;
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
public class AccountController {

    private final AccountService accountService;

    public AccountController(final AccountService accountService) {
        this.accountService = accountService;
    }

    // == GET /login ==
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity<CustomResponse> getLogin() {
        final CustomResponse response = new CustomResponse();
        response.setMessage(Message.UNSUPPORTED);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // == POST /login ==
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<CustomResponse> login(final @Valid @RequestBody LoginRequest form,
                                                final BindingResult bindingResult,
                                                final HttpServletRequest request) {
        final HttpSession session = request.getSession();
        final CustomResponse response = new CustomResponse();

        if (bindingResult.hasErrors()) {
            response.setMessage(Message.REQUEST_FIELD_ISSUE);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
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

    // == GET /viewPortfolio ==
    @RequestMapping(value = "/viewPortfolio", method = RequestMethod.GET)
    public ResponseEntity<?> viewPortfolio(final HttpServletRequest request)  {
        final HttpSession session = request.getSession();
        final Account account = (Account) session.getAttribute(SESSION_ACCOUNT);
        return accountService.viewPortfolio(account);
    }

    // == POST /requestCheck ==
    @RequestMapping(value = "/requestCheck", method = RequestMethod.POST)
    public ResponseEntity<?> requestCheck(
            final @Valid @RequestBody GetCheckRequest form,
            final BindingResult bindingResult,
            final HttpServletRequest request
    ) {
        if (bindingResult.hasErrors()) {
            final CustomResponse response = new CustomResponse();
            response.setMessage(Message.REQUEST_FIELD_ISSUE);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        final HttpSession session = request.getSession();
        final Account account = (Account) session.getAttribute(SESSION_ACCOUNT);

        return accountService.requestCheck(account.getUsername(), form.getCashValue());
    }

    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public ResponseEntity<?> resetPassword(
            final @Valid @RequestBody UpdatePasswordRequest form,
            final BindingResult bindingResult,
            final HttpServletRequest request
    ) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!form.getNewPassword().equals(form.getNewPasswordConfirm())) {
            final CustomResponse response = new CustomResponse();
            response.setMessage(Message.ACCOUNT_PASSWORD_NOT_MATCH);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        final HttpSession session = request.getSession();
        final Account account = (Account) session.getAttribute(SESSION_ACCOUNT);
        return accountService.updatePassword(
                account.getUsername(),
                form.getCurrentPassword(),
                form.getNewPassword()
        );
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public ResponseEntity<?> resetPassword(
            final @Valid @RequestBody UpdateProfileRequest form,
            final BindingResult bindingResult,
            final HttpServletRequest request
    ) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        final HttpSession session = request.getSession();
        final Account account = (Account) session.getAttribute(SESSION_ACCOUNT);
        final boolean isUpdateProfileSuccess = accountService.updateProfile(
                account.getUsername(),
                form.getEmail(),
                form.getName()
        );

        final CustomResponse response = new CustomResponse();
        if (isUpdateProfileSuccess) {
            refreshCurrentSession(session, account.getUsername());
            response.setMessage(Message.PROFILE_UPDATE_SUCCESS);
        } else {
            response.setMessage(Message.PROFILE_UPDATE_FAILED);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void refreshCurrentSession(final HttpSession session, final String username) {
        final Optional<Account> accountOpt = accountService.findByUsername(username);
        accountOpt.ifPresent(account -> session.setAttribute(SESSION_ACCOUNT, account));
    }
}
