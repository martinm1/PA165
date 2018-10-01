package cz.muni.fi.pa165.currency;

import java.math.BigDecimal;
import java.util.Currency;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;


public class CurrencyConvertorImplTest {
    

    private static final Currency USD = Currency.getInstance("USD");
    private static final Currency INR = Currency.getInstance("INR");
    
    private final ExchangeRateTable exchangeRateTable = mock(ExchangeRateTable.class);
    private final CurrencyConvertor currencyConvertor = new CurrencyConvertorImpl(exchangeRateTable);
            
    @Test
    public void testConvert() throws ExternalServiceFailureException {
        // Don't forget to test border values and proper rounding.
        //fail("Test is not implemented yet.");
        
        when(exchangeRateTable.getExchangeRate(USD, INR)).thenReturn(new BigDecimal("73.152"));
        
        assertThat(currencyConvertor.convert(USD, INR, new BigDecimal("1.50"))).isEqualTo(new BigDecimal("109.73"));

    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertWithNullSourceCurrency() throws ExternalServiceFailureException {
        //fail("Test is not implemented yet.");
        //ExchangeRateTable exchangeRateTable = mock(ExchangeRateTable.class);
        when(exchangeRateTable.getExchangeRate(USD, INR)).thenReturn(new BigDecimal("73.152"));
        
        //CurrencyConvertor currencyConvertor = new CurrencyConvertorImpl(exchangeRateTable);
        currencyConvertor.convert(null, INR, new BigDecimal("1.50"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertWithNullTargetCurrency() throws ExternalServiceFailureException {
        //fail("Test is not implemented yet.");
        
        //ExchangeRateTable exchangeRateTable = mock(ExchangeRateTable.class);
        when(exchangeRateTable.getExchangeRate(USD, INR)).thenReturn(new BigDecimal("73.152"));
        
        //CurrencyConvertor currencyConvertor = new CurrencyConvertorImpl(exchangeRateTable);
        currencyConvertor.convert(USD, null, new BigDecimal("1.50"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertWithNullSourceAmount() throws ExternalServiceFailureException {
        //fail("Test is not implemented yet.");
        
        //ExchangeRateTable exchangeRateTable = mock(ExchangeRateTable.class);
        when(exchangeRateTable.getExchangeRate(USD, INR)).thenReturn(new BigDecimal("73.152"));
        
        //CurrencyConvertor currencyConvertor = new CurrencyConvertorImpl(exchangeRateTable);
        currencyConvertor.convert(USD, INR, null);
    }

    @Test(expected = UnknownExchangeRateException.class)
    public void testConvertWithUnknownCurrency() throws ExternalServiceFailureException {
        //fail("Test is not implemented yet.");
        //ExchangeRateTable exchangeRateTable = mock(ExchangeRateTable.class);
        when(exchangeRateTable.getExchangeRate(USD, INR)).thenReturn(null);
        
        //CurrencyConvertor currencyConvertor = new CurrencyConvertorImpl(exchangeRateTable);
        currencyConvertor.convert(USD, INR, new BigDecimal("1.50"));
    }

    @Test
    public void testConvertWithExternalServiceFailure() throws ExternalServiceFailureException {
        //fail("Test is not implemented yet.");
        //when()
        ExternalServiceFailureException externalServiceFailureException = new ExternalServiceFailureException("External error");
        when(exchangeRateTable.getExchangeRate(USD, INR)).thenThrow(externalServiceFailureException);
        assertThatExceptionOfType(UnknownExchangeRateException.class)
            .isThrownBy(()->currencyConvertor.convert(USD, INR, BigDecimal.ONE))
            .withMessage("Lookup for Exchange rate failed")
            .withCause(externalServiceFailureException);
    }

}
