package com.crossover.exchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.crossover.exchange.business.CurrencyBusiness;

@Controller
@RequestMapping("/")
public class CurrencyController {

	@Autowired
	private CurrencyBusiness currencyBusiness;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("name", "Arthur");
        return "greeting";
	}

}
