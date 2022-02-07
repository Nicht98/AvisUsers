package com.bribemap.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.bribemap.common.entity","com.bribemap.admin.user"})
public class BribeBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(BribeBackEndApplication.class, args);
		
	}

}
