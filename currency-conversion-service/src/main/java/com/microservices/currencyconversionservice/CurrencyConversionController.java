package com.microservices.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {
	
	@Autowired
	private CurrencyExchangeServiceProxy proxy;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurreny( @PathVariable String from,
			@PathVariable String to,
			@PathVariable BigDecimal quantity) { 
		
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("to", to);
		uriVariables.put("from", from);
		
		ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
				CurrencyConversionBean.class, uriVariables);
		
		CurrencyConversionBean response = responseEntity.getBody();
		
		return new CurrencyConversionBean(response.getId(), from, to, 
				response.getConversionMultiple(), quantity, 
				quantity.multiply(response.getConversionMultiple()),response.getPort());
	}
	
	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrenyFeign( @PathVariable String from,
			@PathVariable String to,
			@PathVariable BigDecimal quantity) { 
		
		CurrencyConversionBean response = proxy.retrieveExchangeValue(from, to);
		logger.info("{}", response);
		
		return new CurrencyConversionBean(response.getId(), from, to, 
				response.getConversionMultiple(), quantity, 
				quantity.multiply(response.getConversionMultiple()),response.getPort());
	}
}
