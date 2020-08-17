package com.kangmin.app.service.impl;

import com.kangmin.app.dao.AccountDao;
import com.kangmin.app.dao.FundDao;
import com.kangmin.app.dao.PositionDao;
import com.kangmin.app.model.Account;
import com.kangmin.app.model.CustomResponse;
import com.kangmin.app.model.Fund;
import com.kangmin.app.model.Position;
import com.kangmin.app.service.FundService;
import com.kangmin.app.util.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FundServiceImpl implements FundService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FundServiceImpl.class);

    private final FundDao fundDao;
    private final AccountDao accountDao;
    private final PositionDao positionDao;

    public FundServiceImpl(final FundDao fundDao,
                           final AccountDao accountDao,
                           final PositionDao positionDao) {
        this.fundDao = fundDao;
        this.accountDao = accountDao;
        this.positionDao = positionDao;
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
    public boolean isExistBySymbol(final String symbol) {
        return fundDao.existsBySymbol(symbol);
    }

    @Override
    @Transactional
    public Optional<Fund> createFund(final String name,
                                     final String symbol,
                                     final double initialPrice) {
        if (fundDao.existsByNameOrSymbol(name, symbol)) {
            return Optional.empty();
        }

        final Fund fund = new Fund();
        fund.setName(name);
        fund.setSymbol(symbol);
        fund.setPrice(initialPrice);
        final Fund result = fundDao.save(fund);
        return Optional.of(result);
    }

    @Override
    @Transactional
    public synchronized ResponseEntity<?> buyFund(final Account account,
                                                  final String symbol,
                                                  final String cashValue) {
        LOGGER.info(">>>> buyFund in FundService");
        Optional<Fund> fundOpt = fundDao.findBySymbol(symbol);
        CustomResponse response = new CustomResponse();
        if (fundOpt.isPresent()) {
            final Fund fund = fundOpt.get();
            final int shares = (int) (Double.parseDouble(cashValue) / fund.getPrice());
            final Optional<Position> positionOpt = positionDao.findByFundAndAccount(fund, account);

            // == process position's increase ==
            final Position position;
            if (positionOpt.isPresent()) {
                position = positionOpt.get();
                position.setShares(position.getShares() + shares);
            } else {
                position = new Position(fund, account, shares);
            }
            positionDao.save(position);

            // == process account's cash decrease ==
            account.setCash(account.getCash() - Double.parseDouble(cashValue));
            accountDao.save(account);

            response.setMessage(Message.BUY_FUND_SUCCESS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        response.setMessage(Message.FUND_DOES_NOT_EXIST);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
