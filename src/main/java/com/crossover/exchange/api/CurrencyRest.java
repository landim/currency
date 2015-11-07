package com.crossover.exchange.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.crossover.exchange.business.CurrencyBusiness;
import com.crossover.exchange.model.Currency;

@RestController
@RequestMapping("/currency")
public class CurrencyRest {

	@Autowired
	private CurrencyBusiness currencyBusiness;
	
	@RequestMapping(method = RequestMethod.GET)
	List<Currency> getAllCurrencies(@RequestParam(value = "name", required = false) String filterName) {
    	List<Currency> result;
    	if (StringUtils.isEmpty(filterName)) {
    		result = currencyBusiness.findAll();
    	} else {
    		result = currencyBusiness.findByName(filterName);
    	}
		return result;
	}

	@RequestMapping(value = "/convert", method = RequestMethod.GET)
	@ResponseBody
	double convertCurrency(@RequestParam("from") String from, @RequestParam("to") String to, @RequestParam("amount") double amount) {
		return currencyBusiness.convert(from, to, amount);
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	@ResponseBody
	List<Currency> updateCurrencies() {
		return currencyBusiness.updateCurrencies();
	}

}
