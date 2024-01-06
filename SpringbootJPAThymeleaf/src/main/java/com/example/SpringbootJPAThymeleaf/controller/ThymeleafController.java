package com.example.SpringbootJPAThymeleaf.controller;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.SpringbootJPAThymeleaf.model.Book;
import com.example.SpringbootJPAThymeleaf.repo.BookRepo;

//http://localhost:8080/index
@Controller
public class ThymeleafController
{
	private BookRepo bookRepo;

	public ThymeleafController(BookRepo bookRepo)
	{
		this.bookRepo = bookRepo;
	}

	@GetMapping("/systemlogin")
	public ModelAndView systemlogin()
	{
		ModelAndView view = new ModelAndView("systemlogin");
		return view;
	}

	@GetMapping("/bookdetail")
	public ModelAndView getbookdetail(@RequestParam(name = "id") int id)
	{
		ModelAndView view = new ModelAndView("bookdetail");
		view.addObject("book", bookRepo.findById(id).orElse(null));
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
		// create your id logic, sequence or smth
		newBook.setId(new Random().nextInt(100000));
		bookRepo.save(newBook);
		return new ModelAndView("redirect:/index");
	}

	@GetMapping("/deletebook")
	// if you use alternative security config, try these
	// @PreAuthorize(value = "isAuthenticate()")
	//	@PreAuthorize(value = "hasRole('ADMIN')")
	//	@Secured(value = "ROLE_ADMIN")
	public ModelAndView deletebook(@RequestParam(name = "id") int id)
	{
		bookRepo.deleteById(id);
		return new ModelAndView("redirect:/index");
	}

	@GetMapping(path = { "", "/", "/index", "/index.html" })
	public ModelAndView index()
	{
		ModelAndView view = new ModelAndView("books");
		List<Book> books = bookRepo.findAll();
		view.addObject("books", books);
		return view;
	}
}
