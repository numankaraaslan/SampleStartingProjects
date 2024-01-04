package com.example.jakartarestjwt.controller;

import com.example.jakartarestjwt.model.SystemUser;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path(value = "/book")
@RequestScoped
public class BookController
{
	@Inject
	SecurityContext securityContext;

	@Inject
	HttpServletRequest req;

	@GET
	@Path(value = "admin")
	@Produces(value = MediaType.APPLICATION_JSON)
	@RolesAllowed(value = { "ADMIN" })
	public Response admin()
	{
		// localhost:8080/JakartaRestJWT/book/admin
		SystemUser currentUser = (SystemUser) securityContext.getCallerPrincipal();
		return Response.ok().entity("Your token has " + currentUser.getAuthorities() + " roles and username is (" + currentUser.getUsername() + ")").build();
	}

	@GET
	@Path(value = "user")
	@Produces(value = MediaType.APPLICATION_JSON)
	@RolesAllowed(value = { "USER" })
	public Response user()
	{
		// localhost:8080/JakartaRestJWT/book/user
		SystemUser currentUser = (SystemUser) securityContext.getCallerPrincipal();
		return Response.ok().entity("Your token has " + currentUser.getAuthorities() + " roles and username is (" + currentUser.getUsername() + ")").build();
	}
}
