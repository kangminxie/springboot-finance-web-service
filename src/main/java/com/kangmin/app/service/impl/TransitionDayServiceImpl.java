package com.kangmin.app.service.impl;

import com.kangmin.app.dao.FundDao;
import com.kangmin.app.model.Fund;
import com.kangmin.app.model.payload.admin.TransitionDayRequest;
import com.kangmin.app.service.TransitionDayService;
import com.kangmin.app.util.FormatUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class TransitionDayServiceImpl implements TransitionDayService {

    private final FundDao fundDao;

    private static final Object LOCK = new Object();

    public TransitionDayServiceImpl(final FundDao fundDao) {
        this.fundDao = fundDao;
    }

    public Map<String, String> transitionDay(final TransitionDayRequest form) {
        synchronized (LOCK) {
            final List<Fund> funds = fundDao.findAll();
            final Map<String, String> changes = new HashMap<>();

            for (final Fund fund : funds) {
                final double prevValue = fund.getPrice();
                final double newValue = generateNewValue(prevValue);
                fund.setPrice(newValue);
                fundDao.save(fund);
                // == show variations ==
                final double percent = 100 * ((newValue / prevValue) - 1.0);
                final String percentStr = String.format("%s (%.2f%%)", newValue, percent);
                changes.put(fund.getSymbol(), percentStr);
            }

            return changes;
        }
    }

    private double generateNewValue(final double raw) {
        final Random random = new Random();
        return Double.parseDouble(
                FormatUtil.DECIMAL_FORMAT.format(random.nextDouble() * 0.2 * raw + 0.9 * raw)
        );
    }
}
