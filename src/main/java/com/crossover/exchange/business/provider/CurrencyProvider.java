package com.crossover.exchange.business.provider;

import java.util.List;

import com.crossover.exchange.model.Currency;

public interface CurrencyProvider {

	/**
	 * Get all currencies from a provider
	 * @return list of {@link Currency} with code, name and value
	 */
	List<Currency> getAllCurrencies();
}
