package com.coinbase.orderbookservice;

import com.coinbase.orderbookservice.service.CoinbaseOrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderBookServiceApplication implements CommandLineRunner {

	@Autowired
	private CoinbaseOrderService coinbaseOrderService;

	public static void main(String[] args) {
		SpringApplication.run(OrderBookServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws JsonProcessingException {
		if(args.length !=1) {
			throw new RuntimeException("Please pass valid instrument");
		}
		coinbaseOrderService.orderBook(args[0]);
	}
}
