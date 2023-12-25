package com.example.springjdbcrest.controller;

import java.net.URI;
import java.util.List;
import java.util.Random;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springjdbcrest.model.Book;
import com.example.springjdbcrest.repo.BookRepo;

//http://localhost:8080/SpringJDBCRest/book
@RestController
@RequestMapping(path = "book")
public class BookController
{
	private BookRepo bookRepo;

	public BookController(BookRepo bookRepo)
	{
		this.bookRepo = bookRepo;
	}

	@GetMapping(path = "bookdetail/{id}")
	public ResponseEntity<Book> getbookdetail(@PathVariable(name = "id") int id)
	{
		Book book = bookRepo.getByID(id);
		if (book != null)
		{
			return ResponseEntity.ok(book);
		}
		else
		{
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping(path = "save")
	public ResponseEntity<String> addbook(@RequestBody Book newBook)
	{
		// { "name": "somename", "year": 1234, "author": "someauthor" }
		// create your id logic, sequence or smth
		newBook.setId(new Random().nextInt(100000));
		boolean result = bookRepo.save(newBook);
		if (result)
		{
			return ResponseEntity.created(URI.create("localhost:8080/book/bookdetail/" + newBook.getId())).body("Successfully saved");
		}
		else
		{
			return ResponseEntity.internalServerError().body("Error happened");
		}
	}

	@DeleteMapping(path = "deletebook/{id}")
	public ResponseEntity<String> deletebook(@RequestParam(name = "id") int id)
	{
		boolean result = bookRepo.deleteByID(id);
		if (result)
		{
			return ResponseEntity.ok("Successfully deleted");
		}
		else
		{
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping(path = { "", "/" })
	public ResponseEntity<List<Book>> index()
	{
		List<Book> books = bookRepo.getAll();
		return ResponseEntity.ok(books);
	}
}
