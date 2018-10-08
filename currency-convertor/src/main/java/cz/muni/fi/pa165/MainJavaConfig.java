/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165;

import cz.muni.fi.pa165.currency.CurrencyConvertor;
import cz.muni.fi.pa165.currency.CurrencyConvertorImpl;
import java.math.BigDecimal;
import java.util.Currency;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author martin
 */
public class MainJavaConfig {
    public static void main(String[] args){
        ApplicationContext applicatioContext
            = new AnnotationConfigApplicationContext(SpringJavaConfig.class);
        
        CurrencyConvertor currencyConvertor = applicatioContext.getBean(CurrencyConvertor.class);
        
        System.out.println(currencyConvertor.convert(Currency.getInstance("EUR"), Currency.getInstance("CZK"), new BigDecimal(1.0)));
    } 
}
