package com.microservices.currencychangeservice;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {

	@Autowired
	private Environment env;
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
			ExchangeValue exchangeValue = new ExchangeValue(from, to, 1000L, BigDecimal.valueOf(65));
			exchangeValue.setPort(Integer.parseInt(env.getProperty("local.server.port")));
			return exchangeValue;
	}
}

