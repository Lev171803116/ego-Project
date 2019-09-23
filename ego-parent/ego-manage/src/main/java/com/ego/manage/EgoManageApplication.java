package com.ego.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.ego")
public class EgoManageApplication {

	public static void main(String[] args) {
		SpringApplication.run(EgoManageApplication.class, args);
	}

}
