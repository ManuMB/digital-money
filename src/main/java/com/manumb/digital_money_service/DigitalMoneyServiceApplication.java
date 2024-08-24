package com.manumb.digital_money_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

@SpringBootApplication
public class DigitalMoneyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitalMoneyServiceApplication.class, args);
	}

}
