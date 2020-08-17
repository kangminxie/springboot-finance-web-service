package com.kangmin.app.service;

import com.kangmin.app.model.Account;
import com.kangmin.app.model.Fund;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface FundService {

    List<Fund> getAll();

    Optional<Fund> findById(int id);

    Optional<Fund> findByName(String name);

    Optional<Fund> findBySymbol(String symbol);

    boolean isExistBySymbol(String symbol);

    Optional<Fund> createFund(String name, String symbol, double initValue);

    ResponseEntity<?> buyFund(Account account, String symbol, String cashValue);
}
