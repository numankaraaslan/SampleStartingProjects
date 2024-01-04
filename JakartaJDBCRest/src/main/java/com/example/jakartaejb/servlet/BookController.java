package com.example.jakartaejb.servlet;

import java.util.List;

import com.example.jakartaejb.model.Book;
import com.example.jakartaejb.repo.BookRepo;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@RequestScoped
@Path(value = "book")
public class BookController
{
	@Inject
	public BookRepo bookRepo;

	@GET
	@Path(value = "/getall")
	@Produces(value = MediaType.APPLICATION_JSON)
	public Response getall()
	{
		// localhost:8080/JakartaEJB/book/getall
		try
		{
			List<Book> result = bookRepo.getBooks();
			return Response.ok().entity(result).build();

		}
		catch (Exception e)
		{
			return Response.serverError().entity("An exception occured -> " + e.getMessage()).build();

		}
	}

	@GET
	@Path(value = "/getbyidqueryparam")
	@Produces(value = MediaType.APPLICATION_JSON)
	public Response getbyidqueryparam(@QueryParam(value = "id") int id)
	{

		// localhost:8080/JakartaEJB/book/getbyidqueryparam?id=1
		try
		{
			Book result = bookRepo.findById(id);
			if (result != null)
			{
				return Response.ok().entity(result).build();
			}
			else
			{
				return Response.status(Status.NOT_FOUND).entity("Record not found").build();
			}
		}
		catch (Exception e)
		{
			return Response.serverError().entity("An exception occured -> " + e.getMessage()).build();
		}
	}

	@GET
	@Path(value = "/getbyid/{id}")
	@Produces(value = MediaType.APPLICATION_JSON)
	public Response getbyid(@PathParam(value = "id") int id)
	{

		// localhost:8080/JakartaEJB/book/getbyid/1
		try
		{
			Book result = bookRepo.findById(id);
			if (result != null)
			{
				return Response.ok().entity(result).build();
			}
			else
			{
				return Response.status(Status.NOT_FOUND).entity("Record not found").build();
			}
		}
		catch (Exception e)
		{
			return Response.serverError().entity("An exception occured -> " + e.getMessage()).build();
		}
	}

	@GET
	@Path(value = "/getbyidheader")
	@Produces(value = MediaType.APPLICATION_JSON)
	public Response getbyidheader(@HeaderParam(value = "id") int id)
	{
		// localhost:8080/JakartaEJB/book/getbyidheader
		try
		{
			Book result = bookRepo.findById(id);
			if (result != null)
			{
				return Response.ok().entity(result).build();
			}
			else
			{
				return Response.status(Status.NOT_FOUND).entity("Record not found").build();
			}
		}
		catch (Exception e)
		{
			return Response.serverError().entity("An exception occured -> " + e.getMessage()).build();
		}
	}

	@DELETE
	@Path(value = "/deletebyid/{id}")
	@Produces(value = MediaType.TEXT_PLAIN)
	public Response deletebyid(@PathParam(value = "id") long id)
	{
		// localhost:8080/JakartaEJB/book/deletebyid/1
		try
		{
			if (bookRepo.deleteById(id))
			{
				return Response.ok().entity("Successfully deleted").build();
			}
			else
			{
				return Response.status(Status.NOT_FOUND).entity("Nor ecord found").build();
			}
		}
		catch (Exception e)
		{
			return Response.serverError().entity("An exception occured -> " + e.getMessage()).build();
		}
	}

	@POST
	@Path(value = "save")
	@Consumes(value = MediaType.APPLICATION_JSON)
	public Response save(Book book)
	{
		// localhost:8080/JakartaEJB/book/save
		// { "author": "some author", "id": 1, "name": "book name", "year": 2023 }
		try
		{
			if (bookRepo.save(book))
			{
				return Response.status(Status.CREATED).entity("Successfully saved").build();
			}
			else
			{
				return Response.serverError().entity("Save failed").build();
			}
		}
		catch (Exception e)
		{
			return Response.serverError().entity("An exception occured -> " + e.getMessage()).build();
		}
	}
}
