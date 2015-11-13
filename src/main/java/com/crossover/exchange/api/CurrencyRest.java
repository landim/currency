package com.crossover.exchange.api;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.crossover.exchange.business.CurrencyBusiness;
import com.crossover.exchange.exceptions.CurrencyNotFoundException;
import com.crossover.exchange.model.Currency;

@RestController
@RequestMapping("/currency")
public class CurrencyRest {

	@Autowired
	private CurrencyBusiness currencyBusiness;
	
	@RequestMapping(method = RequestMethod.GET)
	List<Currency> getAllCurrencies(@RequestParam(value = "name", required = false) String filterName, HttpServletResponse response) {
    	List<Currency> result;
    	if (StringUtils.isEmpty(filterName)) {
    		result = currencyBusiness.findAll();
    	} else {
    		result = currencyBusiness.findByName(filterName);
    	}
    	
    	if (result == null || result.size() == 0) {
    		response.setStatus(HttpStatus.NOT_FOUND.value());
    	}
		return result;
	}

	@RequestMapping(value = "/convert", method = RequestMethod.GET)
	@ResponseBody
	double convertCurrency(@RequestParam("from") String from, @RequestParam("to") String to, 
			@RequestParam("amount") double amount, HttpServletResponse response) throws CurrencyNotFoundException {
		return currencyBusiness.convert(from, to, amount);
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	@ResponseBody
	List<Currency> updateCurrencies() {
		return currencyBusiness.updateCurrencies();
	}
	
	// Convert a predefined exception to an HTTP Status code
	@ResponseStatus(value=HttpStatus.NOT_FOUND)  // 404
	@ExceptionHandler(CurrencyNotFoundException.class)
	public void exceptionHandling(CurrencyNotFoundException exception, HttpServletResponse response) {
		response.addHeader("reason", exception.getMessage());
	}

}
