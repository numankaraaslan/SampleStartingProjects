package com.bilgeadam.SpringbootJPARest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;

@SpringBootApplication(exclude = JmxAutoConfiguration.class)
public class SpringbootJPARestApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(SpringbootJPARestApplication.class, args);
	}
}