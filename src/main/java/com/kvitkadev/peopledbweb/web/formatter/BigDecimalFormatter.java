package com.kvitkadev.peopledbweb.web.formatter;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

@Component
public class BigDecimalFormatter implements Formatter<BigDecimal> {

    @Override
    public BigDecimal parse(String text, Locale locale) throws ParseException {
        NumberFormat currencyInstance = NumberFormat.getCurrencyInstance(locale);
        return new BigDecimal(currencyInstance.parse(text).toString());
    }

    @Override
    public String print(BigDecimal object, Locale locale) {
        NumberFormat currencyInstance = NumberFormat.getCurrencyInstance(locale);
        return currencyInstance.format(object);
    }
}
