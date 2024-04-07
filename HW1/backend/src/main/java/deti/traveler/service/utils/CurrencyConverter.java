package deti.traveler.service.utils;

import deti.traveler.service.ApiCurrency;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
public class CurrencyConverter
{
    private final ApiCurrency currency;

    private Map<String, Double> currencyValues;

    public double convert(CURRENCY currencyOpt, double toBeConverted)
    {
        log.info("/nChoosen currency : "+currencyOpt.toString());
        final double ExchangeRate = currencyValues.get(currencyOpt.toString());

        return toBeConverted * ExchangeRate;
    }

    public void updateCacheCurrency() throws IOException, InterruptedException {
        this.currency.fetchCurrency();
        this.currencyValues = currency.getCurrencyMap();
    }

    public CurrencyConverter() throws IOException, InterruptedException {
        this.currency = new ApiCurrency();
        this.currency.fetchCurrency();
        this.currencyValues = currency.getCurrencyMap();
    }

}
