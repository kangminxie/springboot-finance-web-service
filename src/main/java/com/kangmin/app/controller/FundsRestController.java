package com.kangmin.app.controller;

import com.kangmin.app.model.Fund;
import com.kangmin.app.service.FundService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class FundsRestController {

    private final FundService fundService;

    public FundsRestController(final FundService fundService) {
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
}
