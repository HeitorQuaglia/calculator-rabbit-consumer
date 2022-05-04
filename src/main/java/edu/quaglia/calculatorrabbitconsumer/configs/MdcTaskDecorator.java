package edu.quaglia.calculatorrabbitconsumer.configs;

import models.Control;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

import java.util.Map;

public class MdcTaskDecorator implements TaskDecorator {

    private final Logger LOGGER = LoggerFactory.getLogger(MdcTaskDecorator.class);

    @Override
    public Runnable decorate(Runnable runnable) {
        LOGGER.info("Runnable {}", runnable);

        Map<String, String> contextMap = MDC.getCopyOfContextMap();

        if (contextMap == null)
            contextMap.put("UNIQUE_ID", Control.mdId);
        try {
            return () -> {
                MDC.setContextMap(contextMap);
                runnable.run();
            };
        } finally {
            MDC.clear();
        }

    }

}