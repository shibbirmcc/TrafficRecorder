package com.adevrtisementserver.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = { "com.adevrtisementserver" })
@EnableScheduling
public class TrafficRecorder {

	public static void main(String[] args) {
		SpringApplication.run(TrafficRecorder.class, args);
	}
}
