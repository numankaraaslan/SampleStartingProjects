package com.example.springjdbcrest.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//jwt token shall be consumeth...
public class JWTAuthorizationFilter extends OncePerRequestFilter
{
	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException
	{
		// unfortunately this logic is invoked whenever the Authorization is sent
		// even though some endpoints are freely open, they are authorized
		// you need a bit more configuration to know which paths requires authorization here
		String token = req.getHeader("Authorization");
		String mytoken = null;
		if (token != null)
		{
			try
			{
				// get rid of "Bearer " if there is any
				mytoken = JWT.require(Algorithm.HMAC512("MY_SECRET_KEY".getBytes())).build().verify(token.replace("Bearer ", "")).getSubject();
			}
			catch (Exception e)
			{
				mytoken = null;
				res.setStatus(HttpStatus.UNAUTHORIZED.value());
				res.getWriter().write("Token exception => " + e.getMessage());
				res.flushBuffer();
			}
			if (mytoken != null)
			{
				// mytoken is username-ROLE_USER,ROLE_ADMIN,ROLE_CONTRIBUTER ...etc
				String username = mytoken.split("-")[0];
				List<GrantedAuthority> auth = Arrays.asList(mytoken.split("-")[1].split(",")).stream().map(item -> new SimpleGrantedAuthority(item)).collect(Collectors.toList());
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, auth);
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				chain.doFilter(req, res);
			}
		}
		else
		{
			// no token, will cause secrity officer to stop me
			chain.doFilter(req, res);
		}
	}
}
