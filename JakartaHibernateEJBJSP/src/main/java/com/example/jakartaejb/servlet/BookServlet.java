package com.example.jakartaejb.servlet;

import java.io.IOException;

import com.example.jakartaejb.model.Book;
import com.example.jakartaejb.repo.BookRepo;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Stateless
// xml alternative here
// @WebServlet(urlPatterns = { "/book/addbook", "/book/books" })
public class BookServlet extends HttpServlet
{
	private static final long serialVersionUID = -4755981094229859819L;

	@Inject
	public BookRepo bookRepo;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if (request.getRequestURI().endsWith("/addbook"))
		{
			request.getRequestDispatcher("addbook.jsp").forward(request, response);
		}
		else if (request.getRequestURI().endsWith("/books"))
		{
			request.setAttribute("books", bookRepo.getBooks());
			request.getRequestDispatcher("list.jsp").forward(request, response);
		}
		else if (request.getRequestURI().endsWith("/bookdetail"))
		{
			// /bookdetail?id=1
			String id = request.getParameter("id");
			request.setAttribute("book", bookRepo.findById(Integer.parseInt(id)));
			request.getRequestDispatcher("bookdetail.jsp").forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if (request.getRequestURI().endsWith("/addbook"))
		{
			bookRepo.save(new Book(request.getParameter("name"), Integer.parseInt(request.getParameter("year")), request.getParameter("author")));
			response.sendRedirect("addbook.jsp");
		}
	}
}
