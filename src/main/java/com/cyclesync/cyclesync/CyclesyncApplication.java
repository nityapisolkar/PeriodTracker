package com.cyclesync.cyclesync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class CyclesyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(CyclesyncApplication.class, args);
	}

}
