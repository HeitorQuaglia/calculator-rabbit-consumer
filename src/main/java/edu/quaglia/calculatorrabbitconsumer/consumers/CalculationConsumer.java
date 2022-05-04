package edu.quaglia.calculatorrabbitconsumer.consumers;

import edu.quaglia.calculatorrabbitconsumer.services.CalculationService;
import models.Calculation;
import models.CalculationResult;
import models.Control;
import models.enums.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
public class CalculationConsumer {

    private final Logger LOGGER = LoggerFactory.getLogger(CalculationConsumer.class);

    private final CalculationService calculationService;

    public CalculationConsumer(CalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @RabbitListener(queues = "#{queue.name}", concurrency = "10")
    public CalculationResult receive(Calculation calculation) {
        if(MDC.getCopyOfContextMap() == null)
            MDC.put("UNIQUE_ID", Control.mdId);

        LOGGER.info("Received an expression {} from queue", calculation);

        LOGGER.info("ControlClass mdId value {}", Control.mdId);

        Objects.requireNonNull(calculation.getA(), "a in expression cannot be null");
        Objects.requireNonNull(calculation.getB(), "b in expression cannot be null");
        Objects.requireNonNull(calculation.getOperation(), "operator in expression cannot be null");

        Operation operator = calculation.getOperation();

        BigDecimal result = null;

        switch (operator) {
            case ADD:
                result = calculationService.add(calculation.getA(), calculation.getB());
                break;
            case SUBTRACT:
                result = calculationService.subtract(calculation.getA(), calculation.getB());
                break;
            case MULTIPLY:
                result = calculationService.multiply(calculation.getA(), calculation.getB());
                break;
            case DIVIDE:
                result = calculationService.divide(calculation.getA(), calculation.getB());
                break;
        }

        LOGGER.info("Evaluated expression {}, and result is {}", calculation, result);

        return new CalculationResult(result);

    }
}
