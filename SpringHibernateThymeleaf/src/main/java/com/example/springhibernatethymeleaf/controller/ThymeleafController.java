package com.example.springhibernatethymeleaf.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.springhibernatethymeleaf.model.Book;
import com.example.springhibernatethymeleaf.repo.BookRepo;

// http://localhost:8080/SpringHibernateThymeleaf/index
@Controller
public class ThymeleafController
{
	private BookRepo bookRepo;

	public ThymeleafController(BookRepo bookRepo)
	{
		this.bookRepo = bookRepo;
	}

	@GetMapping("/bookdetail")
	public ModelAndView getbookdetail(@RequestParam(name = "id") int id)
	{
		ModelAndView view = new ModelAndView("bookdetail");
		view.addObject("book", bookRepo.findById(id));
		return view;
	}

	@GetMapping("/addbook")
	public ModelAndView addbook()
	{
		ModelAndView view = new ModelAndView("addbook");
		view.addObject("newbook", new Book());
		return view;
	}

	@PostMapping("/addbook")
	public ModelAndView addbookPost(@ModelAttribute Book newBook)
	{
		// id comes from sequence
		bookRepo.save(newBook);
		return new ModelAndView("redirect:/index");
	}

	@GetMapping("/deletebook")
	public ModelAndView deletebook(@RequestParam(name = "id") int id)
	{
		bookRepo.deleteById(id);
		return new ModelAndView("redirect:/index");
	}

	@GetMapping(path = { "", "/", "/index", "/index.html" })
	public ModelAndView index()
	{
		ModelAndView view = new ModelAndView("books");
		List<Book> books = bookRepo.getBooks();
		view.addObject("books", books);
		return view;
	}
}
