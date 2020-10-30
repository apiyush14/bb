package com.nexeas.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@EnableConfigurationProperties
@ComponentScan(basePackages= {"com.onehealth.services","com.onehealth.config","com.onehealth.processors"})
@EntityScan(basePackages = {"com.onehealth.entities"})
@EnableJpaRepositories(basePackages = {"com.onehealth.repo"})
public class OnehealthApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnehealthApplication.class, args);
	}

}
