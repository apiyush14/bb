package com.nexeas.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAutoConfiguration
@EnableConfigurationProperties
@ComponentScan(basePackages = { "com.onehealth.services", "com.onehealth.config", "com.onehealth.processors",
		"com.onehealth.auth", "com.onehealth.core.kafka", "com.onehealth.schedulers", "com.nexeas.demo" })
@EntityScan(basePackages = { "com.onehealth.entities" })
@EnableJpaRepositories(basePackages = { "com.onehealth.repo" })
public class OnehealthApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnehealthApplication.class, args);
	}

}
