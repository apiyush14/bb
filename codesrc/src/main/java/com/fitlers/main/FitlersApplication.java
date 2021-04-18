package com.fitlers.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients("com.fitlers.proxy")
@EnableScheduling
@EnableAutoConfiguration
@EnableConfigurationProperties
@ComponentScan(basePackages = { "com.fitlers.services", "com.fitlers.config", "com.fitlers.processors",
		"com.fitlers.auth", "com.fitlers.kafka", "com.fitlers.schedulers", "com.fitlers.demo",
		"com.fitlers.core.config", "com.fitlers.core.encryption", "com.fitlers.proxy","com.fitlers.main"})
@EntityScan(basePackages = { "com.fitlers.entities" })
@EnableJpaRepositories(basePackages = { "com.fitlers.repo" })
public class FitlersApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitlersApplication.class, args);
	}

}
