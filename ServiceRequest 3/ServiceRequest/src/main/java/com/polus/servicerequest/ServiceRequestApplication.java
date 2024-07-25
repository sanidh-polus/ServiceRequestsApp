package com.polus.servicerequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class ServiceRequestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceRequestApplication.class, args);
		
	}

}
