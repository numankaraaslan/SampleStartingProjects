package com.bilgeadam.SpringbootJPARest.controller;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bilgeadam.SpringbootJPARest.model.Book;
import com.bilgeadam.SpringbootJPARest.service.BookService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

// http://localhost:8080/book
// http://localhost:8080/swagger-ui/index.html
// @CrossOrigin(origins = "http://localhost:3000")
@io.swagger.v3.oas.annotations.tags.Tag(description = "Book endpointleri", name = "book")
@RestController
@RequestMapping(path = "book")
public class BookController
{
	private BookService bookService;

	public BookController(BookService bookService)
	{
		this.bookService = bookService;
	}

	@GetMapping(path = { "", "/" })
	public ResponseEntity<List<Book>> index()
	{
		// http://localhost:8080/book
		List<Book> books = bookService.findAll();
		return ResponseEntity.ok(books);
	}

	@PreAuthorize(value = "isAuthenticated()")
	@PostMapping(path = "save")
	public ResponseEntity<String> addbook(@RequestBody Book newBook)
	{
		// http://localhost:8080/book/save
		newBook = bookService.save(newBook);
		if (newBook != null)
		{
			return ResponseEntity.created(URI.create("localhost:8080/book/bookdetail/" + newBook.getId())).body("Successfully saved");
		}
		else
		{
			return ResponseEntity.internalServerError().body("Error happened");
		}
	}

	//	@io.swagger.v3.oas.annotations.Hidden
	@io.swagger.v3.oas.annotations.Operation(parameters = @Parameter(name = "id", description = "Aranan kitap id"), description = "Bulunursa 200 bulunamazsa 404 döndürür", summary = "ID ile getir")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Bulunursa"), @ApiResponse(responseCode = "404", description = "Bulunamazsa", content = @Content(examples = @ExampleObject(value = "burada body olmaz"))) })
	@GetMapping(path = "{id}")
	public ResponseEntity<Book> getbookdetail(@PathVariable(name = "id") int id)
	{
		// http://localhost:8080/book/1
		Book book = bookService.findById(id);
		if (book != null)
		{
			return ResponseEntity.ok(book);
		}
		else
		{
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping(path = "findbyname")
	public ResponseEntity<List<Book>> findbyname(@RequestParam(name = "bookName", required = true) String bookName)
	{
		// http://localhost:8080/book/findbyname?bookName=madonna
		List<Book> books = bookService.findByNameLike(bookName, Sort.by(Order.asc("name")));
		return ResponseEntity.ok(books);
	}

	@GetMapping(path = "findbyauthor")
	public ResponseEntity<List<Book>> findbyauthor(@RequestHeader(name = "authorName", required = true) String authorName)
	{
		// http://localhost:8080/book/findbyauthor (authorName - sabahattin)
		List<Book> books = bookService.findByAuthorName(authorName);
		return ResponseEntity.ok(books);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	//	@Secured(value = "ROLE_ADMIN")
	@DeleteMapping(path = "deletebook/{id}")
	public ResponseEntity<String> deletebook(@PathVariable(name = "id") int id)
	{
		// http://localhost:8080/book/deletebook/1
		Book result = bookService.findById(id);
		if (result != null)
		{
			bookService.deleteByID(id);
			return ResponseEntity.ok("Successfully deleted");
		}
		else
		{
			return ResponseEntity.notFound().build();
		}
	}

}