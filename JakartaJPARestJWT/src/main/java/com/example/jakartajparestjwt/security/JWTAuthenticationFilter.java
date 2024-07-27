package com.example.jakartajparestjwt.security;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.jakartajparestjwt.model.SystemUser;
import com.example.jakartajparestjwt.model.TokenInfo;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response.Status;

public class JWTAuthenticationFilter extends HttpFilter
{
	private static final long serialVersionUID = -443190227476918394L;

	@Override
	protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException
	{
		System.err.println("JWT Shall Be Produceth !!!");
		// localhost:8080/JakartaJPARestJWT/user/login
		// post body is below
		// {"username":"user", "password":"1234"}
		// {"username":"admin", "password":"1234"}
		if (req.getMethod().equals("POST") && req.getContentType().equals(MediaType.APPLICATION_JSON))
		{
			Jsonb jsonb = JsonbBuilder.create();
			SystemUser user = jsonb.fromJson(req.getInputStream(), SystemUser.class);
			// go to db, check if user exists, check password matches etc...
			// if you can find a way to leave it to Jakarta, notify me :)
			// fetch userinfo and roles and etc...
			List<String> authorities = null;
			if (user.getUsername().equals("user") && user.getPassword().equals("1234"))
			{
				authorities = List.of("USER");
			}
			else if (user.getUsername().equals("admin") && user.getPassword().equals("1234"))
			{
				authorities = List.of("ADMIN");
			}
			// then create token and etc...
			TokenInfo tokenInfo = new TokenInfo();
			tokenInfo.setUsername(user.getUsername());
			// rolestring = ROLE_USER,ROLE_ADMIN,ROLE_CONTRIBUTOR // or more roles here if necessary, comma separated
			String rolestring = authorities.stream().collect(Collectors.joining(","));
			// str = ADMIN-rolestring
			String str = user.getUsername() + "-" + rolestring;
			// token = ADMIN-rolestring (in gibberish of course, 150 minutes valid)
			String token = JWT.create().withSubject(str).withExpiresAt(new Date(System.currentTimeMillis() + 9000000)).sign(Algorithm.HMAC512("MY_SECRET_KEY".getBytes()));
			tokenInfo.setToken(token);
			tokenInfo.setAuthorities(authorities);
			res.setStatus(Status.ACCEPTED.getStatusCode());
			res.setContentType(MediaType.APPLICATION_JSON);
			res.getWriter().write(jsonb.toJson(tokenInfo));
			res.getWriter().flush();
		}
	}
}
