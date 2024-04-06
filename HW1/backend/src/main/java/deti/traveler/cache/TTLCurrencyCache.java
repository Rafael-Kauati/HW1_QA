package deti.traveler.cache;

import deti.traveler.service.utils.CURRENCY;
import deti.traveler.service.utils.CurrencyConverter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@Setter
public class TTLCurrencyCache {

    private CurrencyConverter converter;
    private long lastUpdateTime;

    private  int TTL;


    public TTLCurrencyCache(CurrencyConverter converter) {
        this.converter = converter;
        this.lastUpdateTime = System.currentTimeMillis() ;
    }

    public double convertValue(CURRENCY currency, double valueToBeConverted) throws IOException, InterruptedException {
        if (isCacheExpired()) {
            log.warn("\nCache TTL expired");
            updateCache();
        }
        return converter.convert(currency, valueToBeConverted);
    }

    public boolean isCacheExpired() {
        long currentTime = System.currentTimeMillis();
        return currentTime >= lastUpdateTime + TTL;
    }

    private void updateCache() throws IOException, InterruptedException {
        log.warn("\nUpdating cache");
        converter.updateCacheCurrency();
        lastUpdateTime = System.currentTimeMillis() ;
    }
}