package edu.quaglia.calculatorrabbitconsumer.configs;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Slf4jMDCFilterConfig {

    private final Slf4jMDCFilter slf4jMDCFilterFilter;

    public Slf4jMDCFilterConfig(Slf4jMDCFilter slf4jMDCFilterFilter) {
        this.slf4jMDCFilterFilter = slf4jMDCFilterFilter;
    }

    @Bean
    public FilterRegistrationBean servletRegistrationBean() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(slf4jMDCFilterFilter);
        registrationBean.setOrder(2);
        return registrationBean;
    }
}
