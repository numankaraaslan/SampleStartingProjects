package com.example.jakartaejb.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import com.example.jakartaejb.Constants;
import com.example.jakartaejb.model.Book;
import com.example.jakartaejb.repo.BookRepo;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// localhost:8080/JakartaJDBCThymeleaf/book/getall
public class BookController extends HttpServlet
{
	private static final long serialVersionUID = 2654473220287709855L;

	@Inject
	public BookRepo bookRepo;

	private IWebExchange webExchange;

	private TemplateEngine templateEngine;

	@Override
	public void init() throws ServletException
	{
		super.init();
		this.templateEngine = (TemplateEngine) getServletContext().getAttribute(Constants.TemplateEngineAttr);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		this.webExchange = JakartaServletWebApplication.buildApplication(getServletContext()).buildExchange(req, resp);
		// i have no other way that i know to distiguish paths, since servlet answers all get requests here
		String path = webExchange.getRequest().getPathWithinApplication();
		if (path.equals("/books") || path.equals("/") || path.equals("/index"))
		{
			try
			{
				getBooks(resp);
			}
			catch (Exception e)
			{
				// ideally redirect to error page
				// resp.sendRedirect("error");
			}
		}
		else if (path.equals("/bookdetail"))
		{
			try
			{
				getBookById(req, resp);
			}
			catch (Exception e)
			{
				// ideally redirect to error page
				// resp.sendRedirect("error");
			}
		}
		else if (path.equals("/deletebook"))
		{
			try
			{
				deleteBookById(req, resp);
			}
			catch (Exception e)
			{
				// ideally redirect to error page
				// resp.sendRedirect("error");
			}
		}
		else if (path.equals("/addbook"))
		{
			try
			{
				getAddBook(req, resp);
			}
			catch (Exception e)
			{
				// ideally redirect to error page
				// resp.sendRedirect("error");
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		this.webExchange = JakartaServletWebApplication.buildApplication(getServletContext()).buildExchange(req, resp);
		// i have no other way that i know to distiguish paths, since servlet answers all get requests here
		String path = webExchange.getRequest().getPathWithinApplication();
		if (path.equals("/addbook"))
		{
			try
			{
				savebook(req, resp);
			}
			catch (Exception e)
			{
				System.err.println(e.getLocalizedMessage());
				// ideally redirect to error page
				// resp.sendRedirect("error");
			}
		}
	}

	private void getBooks(HttpServletResponse resp) throws SQLException, IOException
	{
		ArrayList<Book> books = new ArrayList<>();
		books = bookRepo.getBooks();
		WebContext context = new WebContext(this.webExchange);
		context.setVariable("books", books);
		this.templateEngine.process("books", context, resp.getWriter());
	}

	private void getAddBook(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		Book book = new Book();
		WebContext context = new WebContext(this.webExchange);
		context.setVariable("newbook", book);
		this.templateEngine.process("addbook", context, resp.getWriter());
	}

	private void getBookById(HttpServletRequest req, HttpServletResponse resp) throws NumberFormatException, SQLException, IOException
	{
		Book book = bookRepo.findById(Integer.parseInt(req.getParameter("id")));
		WebContext context = new WebContext(webExchange);
		context.setVariable("book", book);
		templateEngine.process("bookdetail", context, resp.getWriter());
	}

	private void deleteBookById(HttpServletRequest req, HttpServletResponse resp) throws NumberFormatException, SQLException, IOException
	{
		boolean result = bookRepo.deleteById(Integer.parseInt(req.getParameter("id")));
		if (result)
		{
			resp.sendRedirect("books");
		}
	}

	private void savebook(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		int id = new Random().nextInt(1000000);
		// form fields
		System.err.println(req.getParameter("name"));
		System.err.println(req.getParameter("year"));
		System.err.println(req.getParameter("author"));
		String name = req.getParameter("name");
		int year = Integer.parseInt(req.getParameter("year"));
		String author = req.getParameter("author");
		Book newBook = new Book(id, name, year, author);
		boolean result = bookRepo.save(newBook);
		if (result)
		{
			resp.sendRedirect("books");
		}
	}
}
