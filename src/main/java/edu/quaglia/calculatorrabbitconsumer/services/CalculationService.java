package edu.quaglia.calculatorrabbitconsumer.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Service
public class CalculationService {
    private final Logger LOGGER = LoggerFactory.getLogger(CalculationService.class);
    private final int PRECISION_SIGNED_DECIMALS = 2;

    public BigDecimal add(BigDecimal a, BigDecimal b) {
        LOGGER.info("{} + {}", a, b);
        return a.add(b);
    }

    public BigDecimal subtract(BigDecimal a, BigDecimal b) {
        LOGGER.info("{} - {}", a, b);
        return a.subtract(b);
    }

    public BigDecimal multiply(BigDecimal a, BigDecimal b) {
        LOGGER.info("{} * {}", a, b);
        return a.multiply(b, new MathContext(PRECISION_SIGNED_DECIMALS + 1, RoundingMode.DOWN));
    }

    public BigDecimal divide(BigDecimal a, BigDecimal b) {
        LOGGER.info("{} / {}", a, b);
        return a.stripTrailingZeros().divide(b, new MathContext(PRECISION_SIGNED_DECIMALS, RoundingMode.DOWN));
    }
}
