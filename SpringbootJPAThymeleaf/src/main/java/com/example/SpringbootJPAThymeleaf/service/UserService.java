package com.example.SpringbootJPAThymeleaf.service;

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
		// go to DB and bla bla and return it to security, don't assume there is an actual user with username "user"
		// spring won't check if username and password match, only password :D
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

}
