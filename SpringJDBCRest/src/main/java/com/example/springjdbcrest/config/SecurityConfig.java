package com.example.springjdbcrest.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfig
{
	@Bean
	public SecurityFilterChain seConfig(HttpSecurity http) throws Exception
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
		AuthenticationManager manager = new ProviderManager(getMeTheProvider());
		//		http.authenticationProvider(getMeTheProvider());
		http.authenticationManager(manager);
		// no need to allow /login endpoint, the filter order is assured here
		http.addFilter(new JWTAuthenticationFilter(manager));
		http.addFilterAfter(new JWTAuthorizationFilter(), JWTAuthenticationFilter.class);
		// fun part starts here
		//		http.authorizeHttpRequests(customizer -> customizer.requestMatchers(HttpMethod.POST).authenticated().anyRequest().permitAll());
		//		http.authorizeHttpRequests(customizer -> customizer.requestMatchers("*/save").authenticated().anyRequest().permitAll());
		//		http.authorizeHttpRequests(customizer -> customizer.requestMatchers("*/save").hasRole("ADMIN").anyRequest().permitAll());
		http.authorizeHttpRequests(customizer -> customizer.anyRequest().permitAll());
		return http.build();
	}

	private AuthenticationProvider getMeTheProvider()
	{
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(new BCryptPasswordEncoder());
		provider.setUserDetailsService(new UserDetailsService()
		{
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
			{
				// go to DB with username and bla bla and return it to security
				// password is 1234 bcrypted
				if (username.equals("admin"))
				{
					return User.withUsername("admin").password("$2a$10$V2UuPZLY7Pzu6ihGbA0Yc.lCHmZ7KCr0Ahdm5IPcYkES/HO0bD1NO").authorities("ROLE_ADMIN").build();
				}
				if (username.equals("user"))
				{
					return User.withUsername("user").password("$2a$10$V2UuPZLY7Pzu6ihGbA0Yc.lCHmZ7KCr0Ahdm5IPcYkES/HO0bD1NO").authorities("ROLE_USER").build();
				}
				else
				{
					throw new UsernameNotFoundException("Username must be 'admin' or 'user'");
				}
			}
		});
		return provider;
	}
}
