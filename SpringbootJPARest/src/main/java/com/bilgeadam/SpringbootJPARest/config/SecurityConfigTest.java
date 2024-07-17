package com.bilgeadam.SpringbootJPARest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Profile(value = "test")
@Configuration
public class SecurityConfigTest
{
	@Bean
	public SecurityFilterChain configureTest(HttpSecurity http, @Autowired AuthenticationConfiguration authenticationConfiguration) throws Exception
	{
		http.csrf(customizer -> customizer.disable());
		http.sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.authorizeHttpRequests(customizer -> customizer.anyRequest().permitAll());
		return http.build();
	}
}
