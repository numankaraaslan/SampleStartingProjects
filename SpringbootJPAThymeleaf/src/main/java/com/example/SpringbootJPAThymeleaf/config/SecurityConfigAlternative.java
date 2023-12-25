package com.example.SpringbootJPAThymeleaf.config;

import java.io.IOException;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//can use @secured or @preauthorize instead of request matchers
//@Configuration
//@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfigAlternative
{
	//	@Bean
	public SecurityFilterChain seConfig(HttpSecurity http, AuthenticationConfiguration configuration) throws Exception
	{
		AuthenticationFailureHandler loginfailhandler = new AuthenticationFailureHandler()
		{
			@Override
			public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException
			{
				System.err.println("Do smth with this i guess => " + exception.getClass() + " - " + exception.getMessage());
				// for some weird reason, this gets 404 ????? So no params...
				// response.sendRedirect("/login?error");
				response.sendRedirect("/login");
			}
		};
		// spring boot security starter has a login screen, customize controller for a custom login scren
		// failure url or handler, who comes last, overrides other, so chose one !!
		http.formLogin(customizer -> customizer.defaultSuccessUrl("/").failureUrl("/login?error=1").failureHandler(loginfailhandler));
		http.logout(customizer -> customizer.logoutSuccessUrl("/"));
		return http.build();
	}
}
