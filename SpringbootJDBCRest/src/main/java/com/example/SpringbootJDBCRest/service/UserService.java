package com.example.SpringbootJDBCRest.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

// automatically picked up by boot
@Component
public class UserService implements UserDetailsService
{
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		// go to DB with username and bla bla and return it to security
		// password is 1234 bcrypted
		return User.withUsername("user").password("$2a$10$V2UuPZLY7Pzu6ihGbA0Yc.lCHmZ7KCr0Ahdm5IPcYkES/HO0bD1NO").authorities(new SimpleGrantedAuthority("ROLE_USER")).build();
		//		return User.withUsername("admin").password("$2a$10$V2UuPZLY7Pzu6ihGbA0Yc.lCHmZ7KCr0Ahdm5IPcYkES/HO0bD1NO").authorities(new SimpleGrantedAuthority("ROLE_ADMIN")).build();
	}

}
