package cz.muni.fi.pa165.currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import org.slf4j.*;


/**
 * This is base implementation of {@link CurrencyConvertor}.
 *
 * @author petr.adamek@embedit.cz
 */
public class CurrencyConvertorImpl implements CurrencyConvertor {

    private final ExchangeRateTable exchangeRateTable;
    private final Logger logger = LoggerFactory.getLogger(CurrencyConvertorImpl.class);

    public CurrencyConvertorImpl(ExchangeRateTable exchangeRateTable) {
        this.exchangeRateTable = exchangeRateTable;
    }

    @Override
    public BigDecimal convert(Currency sourceCurrency, Currency targetCurrency, BigDecimal sourceAmount) {
        BigDecimal exchangeRate;
        BigDecimal targetAmount = null;
        
        if(sourceCurrency == null || targetCurrency == null || sourceAmount == null) throw new IllegalArgumentException();
        
        try {
            exchangeRate = exchangeRateTable.getExchangeRate(sourceCurrency, targetCurrency);
            if(exchangeRate == null) throw new UnknownExchangeRateException("Currency is unknown.");
            targetAmount = sourceAmount.multiply(exchangeRate).setScale(2, RoundingMode.HALF_EVEN);
            
        } catch (ExternalServiceFailureException ex) {
            //Logger.getLogger(CurrencyConvertorImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new UnknownExchangeRateException("Lookup for Exchange rate failed", ex);
        }
        
        logger.info("nekdo pouzil convert");
        
        return targetAmount;
    }

}
