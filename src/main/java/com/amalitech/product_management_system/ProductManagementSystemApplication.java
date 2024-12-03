package com.amalitech.product_management_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class ProductManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductManagementSystemApplication.class, args);
	}

}
