package com.example.springjdbcrest.config;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.springjdbcrest.model.SystemUser;
import com.example.springjdbcrest.model.TokenInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//this is /login post request
//jwt token shall be produceth...
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
	private AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager)
	{
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException
	{
		SystemUser creds = null;
		try
		{
			creds = new ObjectMapper().readValue(req.getInputStream(), SystemUser.class);
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
		// userservice is invoked here
		return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword()));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException
	{
		// comes from userservice
		User principal = ((User) auth.getPrincipal());
		// rolestring = ROLE_USER,ROLE_ADMIN,ROLE_CONTRIBUTOR
		String rolestring = principal.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.joining(","));
		String str = principal.getUsername() + "-" + rolestring;
		String token = JWT.create().withSubject(str).withExpiresAt(new Date(System.currentTimeMillis() + 9000000)).sign(Algorithm.HMAC512("MY_SECRET_KEY".getBytes()));
		List<String> list = principal.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
		TokenInfo body = new TokenInfo(principal.getUsername(), list, token);
		res.setContentType(MediaType.APPLICATION_JSON_VALUE);
		res.getWriter().write(new ObjectMapper().writeValueAsString(body));
		res.getWriter().flush();
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException
	{
		// you don't need an AuthenticationFailureHandler implementation distinctly
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		if (failed.getClass() == DisabledException.class)
		{
			response.getWriter().write("user is disabled");
		}
		else if (failed.getClass() == BadCredentialsException.class)
		{
			response.getWriter().write("Username password wrong");
		}
		else
		{
			response.getWriter().write("Some error occured");
		}
		response.getWriter().flush();
	}
}