package com.crossover.exchange.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such Order")
public class CurrencyNotFoundException extends Exception {

	public CurrencyNotFoundException(String currency) {
		super("Currency not found: " + currency);
	}

}
