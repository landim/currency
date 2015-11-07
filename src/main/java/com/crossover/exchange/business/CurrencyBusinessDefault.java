package com.crossover.exchange.business;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crossover.exchange.business.provider.CurrencyProvider;
import com.crossover.exchange.model.Currency;
import com.crossover.exchange.model.CurrencyRepository;

@Component
public class CurrencyBusinessDefault implements CurrencyBusiness {

	@Autowired
	CurrencyRepository currencyRepository;
	
	@Autowired
	CurrencyProvider currencyProvider;
	
	@Override
	public List<Currency> findAll() {
		return currencyRepository.findAll();
	}

	@Override
	public List<Currency> updateCurrencies() {
		List<Currency> currenciesToUpdate = currencyProvider.getAllCurrencies();
		currencyRepository.deleteAll();
		currencyRepository.insert(currenciesToUpdate);
		return currenciesToUpdate;
	}

	@Override
	public List<Currency> findByName(String name) {
		//splitting name by whitespaces and generating the search string ".*(token1).*(token2).*"
		StringBuffer searchString = new StringBuffer(".*");
		Arrays.stream(name.split("[\\s\\+]")).forEach(token -> searchString.append("(" + token + ").*"));
		System.out.println(searchString.toString());
		return currencyRepository.findByNameRegex(searchString.toString());
	}

	@Override
	public double convert(String fromCode, String toCode, double amount) {
		Currency fromCurrency = currencyRepository.findByCode(fromCode);
		Currency toCurrency = currencyRepository.findByCode(toCode);

		//calculating conversion
		if (fromCurrency.getValue() == 0) {
			return 0; //avoiding divisionByZero
		}
		return (toCurrency.getValue() / fromCurrency.getValue()) * amount;
	}

}
