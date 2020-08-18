package com.kangmin.app.controller.rest;

import com.kangmin.app.model.Account;
import com.kangmin.app.model.response.CustomResponse;
import com.kangmin.app.model.Fund;
import com.kangmin.app.model.payload.custom.BuyFundRequest;
import com.kangmin.app.model.payload.custom.SellFundRequest;
import com.kangmin.app.service.FundService;
import com.kangmin.app.util.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.kangmin.app.util.AttributeName.SESSION_ACCOUNT;

@RestController
public class FundsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FundsController.class);

    private final FundService fundService;

    public FundsController(final FundService fundService) {
        this.fundService = fundService;
    }

    @RequestMapping(value = {"/funds"}, method = RequestMethod.GET)
    public ResponseEntity<List<Fund>> getAllFunds() {
        final List<Fund> funds = fundService.getAll();
        return new ResponseEntity<>(funds, HttpStatus.OK);
    }

    @RequestMapping(value = {"/funds/{id}"}, method = RequestMethod.GET)
    public ResponseEntity<Fund> getIndividualFund(final @PathVariable int id) {
        final Optional<Fund> fundOpt = fundService.findById(id);
        return new ResponseEntity<>(fundOpt.orElse(null), HttpStatus.OK);
    }

    @RequestMapping(value = {"/funds/buy"}, method = RequestMethod.POST)
    public ResponseEntity<?> buyFund(
            final @RequestBody BuyFundRequest form,
            final @Valid BindingResult bindingResult,
            final HttpServletRequest request
    ) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        final CustomResponse response = new CustomResponse();
        if (fundService.isNotExistBySymbol(form.getSymbol())) {
            response.setMessage(Message.FUND_DOES_NOT_EXIST);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        final HttpSession session = request.getSession();
        final Account account = (Account) session.getAttribute(SESSION_ACCOUNT);
        if (account.getCash() < Double.parseDouble(form.getCashValue())) {
            response.setMessage(Message.INSUFFICIENT_CASH_BUY_FUND);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return fundService.buyFund(account, form.getSymbol(), form.getCashValue());
    }

    @RequestMapping(value = {"/funds/sell"}, method = RequestMethod.POST)
    public ResponseEntity<?> sellFund(
            final @RequestBody SellFundRequest form,
            final @Valid BindingResult bindingResult,
            final HttpServletRequest request
    ) {
        LOGGER.info(">>> in Controller received dto for sellFundForm:" + form);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        final CustomResponse response = new CustomResponse();
        if (fundService.isNotExistBySymbol(form.getSymbol())) {
            response.setMessage(Message.FUND_DOES_NOT_EXIST);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        final HttpSession session = request.getSession();
        final Account account = (Account) session.getAttribute(SESSION_ACCOUNT);
        return fundService.sellFund(account, form.getSymbol(), form.getNumShares());
    }
}
