package com.example.SpringbootJPAThymeleaf.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.ForwardAuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig
{
	@Bean
	public SecurityFilterChain seConfig(HttpSecurity http, AuthenticationConfiguration configuration) throws Exception
	{
		AuthenticationFailureHandler loginfailhandler = new AuthenticationFailureHandler()
		{
			@Override
			public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException
			{
				System.err.println("Do smth with this i guess => " + exception.getClass() + " - " + exception.getMessage());
				// for some weird reason, this gets 404 ????? So no params... Or redirect to your custom page i guess
				// response.sendRedirect("/login?error");
				response.sendRedirect("/systemlogin");
			}
		};
		// spring boot security starter has a login screen, customize controller for a custom login scren
		// failure url or handler, chose one !!
		//http.formLogin(customizer -> customizer.defaultSuccessUrl("/").failureUrl("/systemlogin?error=1"));
		//http.formLogin(customizer -> customizer.defaultSuccessUrl("/").failureHandler(loginfailhandler));
		http.formLogin(customizer -> customizer.loginPage("/systemlogin").loginProcessingUrl("/login").defaultSuccessUrl("/").failureHandler(new ForwardAuthenticationFailureHandler("/systemlogin?failed")).failureUrl("/systemlogin?failed").failureForwardUrl("/systemlogin?failed"));
		http.logout(customizer -> customizer.logoutSuccessUrl("/"));
		//		http.authorizeHttpRequests(customizer -> customizer.requestMatchers(HttpMethod.POST).authenticated().anyRequest().permitAll());
		//		http.authorizeHttpRequests(customizer -> customizer.requestMatchers("*deletebook*").authenticated().anyRequest().permitAll());
		//		http.authorizeHttpRequests(customizer -> customizer.requestMatchers("*deletebook*").hasRole("ADMIN").anyRequest().permitAll());
		http.authorizeHttpRequests(customizer -> customizer.anyRequest().permitAll());
		return http.build();
	}
}
