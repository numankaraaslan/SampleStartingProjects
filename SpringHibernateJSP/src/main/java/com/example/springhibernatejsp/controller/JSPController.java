package com.example.springhibernatejsp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.springhibernatejsp.model.Book;
import com.example.springhibernatejsp.repo.BookRepo;

// localhost:8080/SpringHibernateJSP
@Controller
public class JSPController
{
	private BookRepo bookService;

	public JSPController(BookRepo bookService)
	{
		this.bookService = bookService;
	}

	@GetMapping(path = { "", "/", "/index", "/books", "/index.jsp" })
	public ModelAndView getBooks()
	{
		ModelAndView booksJSP = new ModelAndView("books");
		booksJSP.addObject("books", bookService.getBooks());
		return booksJSP;
	}

	@GetMapping("/addbook")
	public ModelAndView addbook()
	{
		ModelAndView booksJSP = new ModelAndView("addbook");
		booksJSP.addObject("book", new Book());
		return booksJSP;
	}

	@GetMapping("/bookdetail")
	public ModelAndView bookdetail(@RequestParam(name = "id") int id)
	{
		ModelAndView bookdetailJPS = new ModelAndView("bookdetail");
		bookdetailJPS.addObject("book", bookService.findById(id));
		return bookdetailJPS;
	}

	@GetMapping("/deletebook")
	public ModelAndView deletebook(@RequestParam(name = "id") int id)
	{
		bookService.deleteById(id);
		return new ModelAndView("redirect:books");
	}

	@PostMapping("/addbook")
	public ModelAndView addbookPost(@ModelAttribute Book newBook)
	{
		bookService.save(newBook);
		return new ModelAndView("redirect:books");
	}
}
