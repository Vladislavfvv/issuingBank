package com.edme.issuingBank;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.edme.issuingBank.model")
@EnableJpaRepositories("com.edme.issuingBank.repositories")
@EnableCaching
@EnableDiscoveryClient
@EnableRabbit
public class IssuingBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(IssuingBankApplication.class, args);
	}

}
