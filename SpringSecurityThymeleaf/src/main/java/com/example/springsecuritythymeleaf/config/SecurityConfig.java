package com.example.springsecuritythymeleaf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.ForwardAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig
{
	@Bean
	public SecurityFilterChain mySecurityFilterChainBean(HttpSecurity http) throws Exception
	{
		http.csrf(customizer -> customizer.disable());
		http.authorizeHttpRequests(customizer -> customizer.requestMatchers("secureindex").hasRole("ADMIN").anyRequest().permitAll());
		http.formLogin(customizer -> customizer.loginPage("/systemlogin").defaultSuccessUrl("/index").loginProcessingUrl("/login").failureHandler(new ForwardAuthenticationFailureHandler("/systemlogin?failed")).failureUrl("/systemlogin?failed").failureForwardUrl("/systemlogin?failed"));
		http.logout(customizer -> customizer.logoutUrl("/logout").logoutSuccessUrl("/index"));
		http.httpBasic(Customizer.withDefaults());
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(new BCryptPasswordEncoder());
		provider.setUserDetailsService(new UserDetailsService()
		{
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
			{
				// go to DB, select from users where username = username and etc...
				// password is "1234" bcrypted
				// hand it over to spring security
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
		http.authenticationProvider(provider);
		return http.build();

	}
}
