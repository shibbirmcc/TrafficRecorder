package com.adevrtisementserver.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.adevrtisementserver"} )
public class TrafficRecorder {

	public static void main(String[] args) {
		SpringApplication.run(TrafficRecorder.class, args);
	}
}
