package com.example.jakartajparestjwt.security;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.jakartajparestjwt.model.SystemUser;

import jakarta.enterprise.context.RequestScoped;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RequestScoped
public class JWTAuthorizationMechanism implements HttpAuthenticationMechanism
{
	@Override
	public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext context) throws AuthenticationException
	{
		System.err.println("JWT Shall Be Consumeth !!!");
		String headerInfo = request.getHeader("Authorization");
		String mytoken = null;
		if (headerInfo != null)
		{
			try
			{
				// get rid of "Bearer " if there is any
				mytoken = JWT.require(Algorithm.HMAC512("MY_SECRET_KEY".getBytes())).build().verify(headerInfo.replace("Bearer ", "")).getSubject();
			}
			catch (Exception e)
			{
				return context.responseUnauthorized();
			}
			if (mytoken != null)
			{
				// mytoken is username-ROLE_USER,ROLE_ADMIN,ROLE_CONTRIBUTER ...etc
				String username = mytoken.split("-")[0];
				Set<String> auth = Arrays.asList(mytoken.split("-")[1].split(",")).stream().collect(Collectors.toSet());
				SystemUser myPrincipal = null;
				myPrincipal = new SystemUser(username);
				// setting the roles inside the principal for extra info, keep anything you want inside
				myPrincipal.setAuthorities(auth);
				return context.notifyContainerAboutLogin(myPrincipal, auth);
			}
		}
		return context.doNothing();
	}
}
