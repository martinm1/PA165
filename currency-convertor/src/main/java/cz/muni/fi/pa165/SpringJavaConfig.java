/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165;

import cz.muni.fi.pa165.currency.CurrencyConvertor;
import cz.muni.fi.pa165.currency.CurrencyConvertorImpl;
import cz.muni.fi.pa165.currency.ExchangeRateTable;
import cz.muni.fi.pa165.currency.ExchangeRateTableImpl;
import javax.inject.Inject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 *
 * @author martin
 */
@Configuration
//@ComponentScan("cz.muni.fi.pa165.currency")
@EnableAspectJAutoProxy
public class SpringJavaConfig {
   
    @Bean
    public ExchangeRateTable exchangeRateTable() {
        return new ExchangeRateTableImpl();
    }
    
    @Bean
    public CurrencyConvertor currencyConvertor(ExchangeRateTable exchangeRateTable) {
        return new CurrencyConvertorImpl(exchangeRateTable);
    }
    
    @Bean
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }
    
}
