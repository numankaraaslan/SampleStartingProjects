package com.example.jakartarestjwt.controller;

import com.example.jakartarestjwt.model.SystemUser;

// imports are important !!!!
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Singleton;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path(value = "/book")
@Singleton
public class BookController
{

	@GET
	@Path(value = "admin")
	@Produces(value = MediaType.APPLICATION_JSON)
	@RolesAllowed(value = { "ADMIN" })
	public Response admin(@Context SecurityContext securityContext)
	{
		// localhost:8080/JakartaRestJWT/book/admin
		SystemUser currentUser = (SystemUser) securityContext.getUserPrincipal();
		return Response.ok().entity("Your token has " + currentUser.getAuthorities() + " roles and username is (" + currentUser.getUsername() + ")").build();
	}

	@GET
	@Path(value = "user")
	@Produces(value = MediaType.APPLICATION_JSON)
	@RolesAllowed(value = { "USER" })
	public Response user(@Context SecurityContext securityContext, @Context HttpServletRequest request, @Context HttpServletResponse response)
	{
		// localhost:8080/JakartaRestJWT/book/user
		System.err.println(request.getRequestURL());
		// you can make redirects or something
		System.err.println(response.getStatus());
		SystemUser currentUser = (SystemUser) securityContext.getUserPrincipal();
		return Response.ok().entity("Your token has " + currentUser.getAuthorities() + " roles and username is (" + currentUser.getUsername() + ")").build();
	}
}
