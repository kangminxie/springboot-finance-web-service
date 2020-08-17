package com.kangmin.app.service.impl;

import com.kangmin.app.dao.FundDao;
import com.kangmin.app.model.Fund;
import com.kangmin.app.service.FundService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FundServiceJpaImpl implements FundService {

    private final FundDao fundDao;

    public FundServiceJpaImpl(final FundDao fundDao) {
        this.fundDao = fundDao;
    }

    @Override
    public List<Fund> getAll() {
        return fundDao.findAll();
    }

    @Override
    public Optional<Fund> findById(final int id) {
        return fundDao.findById(id);
    }

    @Override
    public Optional<Fund> findByName(final String name) {
        return fundDao.findByName(name);
    }

    @Override
    public Optional<Fund> findBySymbol(final String symbol) {
        return fundDao.findBySymbol(symbol);
    }

    @Override
    @Transactional
    public Optional<Fund> createFund(final String name,
                                     final String symbol,
                                     final double initialPrice) {
        final Optional<Fund> checkExist = fundDao.findByNameOrSymbol(name, symbol);
        if (checkExist.isPresent()) {
            return Optional.empty();
        }

        final Fund fund = new Fund();
        fund.setName(name);
        fund.setSymbol(symbol);
        fund.setPrice(initialPrice);
        final Fund result = fundDao.save(fund);
        return Optional.of(result);
    }
}
