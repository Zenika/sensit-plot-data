package com.zenika.sensit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SensitPlotterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SensitPlotterApplication.class, args);
	}
	
	@Bean
	@Value("${sensit.token}")
	public Sensit sensit(String token) {
	    Sensit s = new Sensit(token);
	    return s;
	}
}
