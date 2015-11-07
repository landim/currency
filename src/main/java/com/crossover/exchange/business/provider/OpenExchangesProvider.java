package com.crossover.exchange.business.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.crossover.exchange.model.Currency;

/**
 * Implementation of {@link CurrencyProvider} accessing the server OpenExchangeRates.org
 */
@Component
public class OpenExchangesProvider implements CurrencyProvider {

	@Value("${openExchangeAPIId}")
	private String apiId;
	
	@Override
	public List<Currency> getAllCurrencies() {
		List<Currency> currencies = new ArrayList<Currency>();
		RestTemplate restTemplate = new RestTemplate();
		//getting codes and names of currencies
        Map<String, String> currenciesName = restTemplate.getForObject("https://openexchangerates.org/api/currencies.json?app_id=" + apiId, Map.class);
        
        //getting rates of currencies
        OpenExchangeCurrencies currenciesFromServer = restTemplate.getForObject("https://openexchangerates.org/api/latest.json?app_id=" + apiId, OpenExchangeCurrencies.class);
        for (String code : currenciesFromServer.getRates().keySet()) {
			currencies.add(new Currency(code, currenciesName.get(code), currenciesFromServer.getRates().get(code)));
		}
		return currencies;
	}

}
