package com.jakal;

import org.springframework.boot.SpringApplication;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Application {
	@Value("${spring.mail.password}")
	static String pass;

    public static void main(String[] args) {
    	 SpringApplication.run(Application.class, args);
    }
}
