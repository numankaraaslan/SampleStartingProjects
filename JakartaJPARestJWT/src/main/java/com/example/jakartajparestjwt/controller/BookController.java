package com.example.jakartajparestjwt.controller;

import java.net.URI;
import java.util.List;

import com.example.jakartajparestjwt.model.Book;
import com.example.jakartajparestjwt.model.SystemUser;
import com.example.jakartajparestjwt.repo.BookRepository;

// imports are important !!!!
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path(value = "/book")
@Singleton
public class BookController
{
	@Inject
	public BookRepository bookRepository;

	@GET
	@Path(value = "admin")
	@Produces(value = MediaType.APPLICATION_JSON)
	@RolesAllowed(value = { "ADMIN" })
	public Response admin(@Context SecurityContext securityContext)
	{
		// localhost:8080/JakartaJPARestJWT/book/admin
		SystemUser currentUser = (SystemUser) securityContext.getUserPrincipal();
		return Response.ok().entity("Your token has " + currentUser.getAuthorities() + " roles and username is (" + currentUser.getUsername() + ")").build();
	}

	@GET
	@Path(value = "user")
	@Produces(value = MediaType.APPLICATION_JSON)
	@RolesAllowed(value = { "USER" })
	public Response user(@Context SecurityContext securityContext, @Context HttpServletRequest request, @Context HttpServletResponse response)
	{
		// localhost:8080/JakartaJPARestJWT/book/user
		System.err.println(request.getRequestURL());
		// you can make redirects or something
		System.err.println(response.getStatus());
		SystemUser currentUser = (SystemUser) securityContext.getUserPrincipal();
		return Response.ok().entity("Your token has " + currentUser.getAuthorities() + " roles and username is (" + currentUser.getUsername() + ")").build();
	}

	@POST
	@Path(value = "save")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response save(Book book)
	{
		// localhost:8080/JakartaJPARestJWT/book/save
		book = bookRepository.save(book);
		return Response.created(URI.create("localhost:8080/JakartaJPARestJWT/book/" + book.getId())).entity("Created").build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Book> getAllEntities()
	{
		// localhost:8080/JakartaJPARestJWT/book
		return bookRepository.getBooks();
	}

	@GET
	@Path(value = "/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Book getById(@PathParam(value = "id") long id)
	{
		// localhost:8080/JakartaJPARestJWT/1
		return bookRepository.getById(id);
	}
}
