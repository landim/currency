package com.crossover.exchange.business;

import java.util.List;

import org.springframework.stereotype.Component;

import com.crossover.exchange.model.Currency;

@Component
public interface CurrencyBusiness {
	List<Currency> findAll();
	List<Currency> updateCurrencies();
	List<Currency> findByName(String name);
	double convert(String fromCode, String toCode, double value);
}
