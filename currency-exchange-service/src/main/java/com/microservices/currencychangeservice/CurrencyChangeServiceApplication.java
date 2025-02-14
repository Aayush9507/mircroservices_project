package com.microservices.currencychangeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;


import brave.sampler.Sampler;
import javassist.expr.NewArray;

@SpringBootApplication
@EnableDiscoveryClient
public class CurrencyChangeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyChangeServiceApplication.class, args);
	}
	
	@Bean
	public Sampler defaultSampler() {
		return Sampler.ALWAYS_SAMPLE;
	}

}
