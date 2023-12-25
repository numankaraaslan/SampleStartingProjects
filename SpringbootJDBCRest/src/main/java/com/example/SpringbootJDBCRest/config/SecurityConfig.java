package com.example.SpringbootJDBCRest.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class SecurityConfig
{
	@Bean
	public SecurityFilterChain seConfig(HttpSecurity http, AuthenticationConfiguration configuration) throws Exception
	{
		// no security at all !! use CsrfTokenRepository and specific cors configurations
		http.csrf(customizer -> customizer.disable());
		CorsConfigurationSource corsconsfigurationsource = new CorsConfigurationSource()
		{
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request)
			{
				CorsConfiguration config = new CorsConfiguration();
				config.setAllowedHeaders(List.of("*"));
				config.setAllowedOrigins(List.of("*"));
				config.setAllowedMethods(List.of("*"));
				return config;
			}
		};
		http.cors(customizer -> customizer.configurationSource(corsconsfigurationsource));
		http.sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		// no need to allow /login endpoint, the filter order is assured here
		http.addFilter(new JWTAuthenticationFilter(configuration.getAuthenticationManager()));
		http.addFilterAfter(new JWTAuthorizationFilter(), JWTAuthenticationFilter.class);
		// fun part starts here
		//		http.authorizeHttpRequests(customizer -> customizer.requestMatchers(HttpMethod.POST).authenticated().anyRequest().permitAll());
		//		http.authorizeHttpRequests(customizer -> customizer.requestMatchers("*/save").authenticated().anyRequest().permitAll());
		//		http.authorizeHttpRequests(customizer -> customizer.requestMatchers("*/save").hasRole("ADMIN").anyRequest().permitAll());
		http.authorizeHttpRequests(customizer -> customizer.anyRequest().permitAll());
		return http.build();
	}
}
