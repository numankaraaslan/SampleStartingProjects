package com.bilgeadam.SpringbootJPARest;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.bilgeadam.SpringbootJPARest.controller.BookController;
import com.bilgeadam.SpringbootJPARest.model.Book;
import com.bilgeadam.SpringbootJPARest.service.BookService;

// When using JUnit 4, this annotation should be used in combination with @RunWith(SpringRunner.class)
@WebMvcTest(controllers = BookController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
//@Import(value = YeniSecurityConfig.class)
public class ControllerTests
{
	@Autowired
	public MockMvc mock;

	@MockBean
	public BookService bookService;

	@Test
	public void getOgrenciById() throws Exception
	{
		Book originalBook = new Book(1, "Kürk mantolu madonna", "123", 1943, "Sabahattin Ali");
		Mockito.when(bookService.findById(1)).thenReturn(originalBook);
		// {"id": 1, "name": "Kürk mantolu madonna", "isbn": "123", "year": 1943, "author": "Sabahattin Ali"}
		RequestBuilder request = MockMvcRequestBuilders.get("/book/1");
		// assertion gibi expectation yazabilirim
		ResultMatcher id = MockMvcResultMatchers.jsonPath("$.id").value(1);
		ResultMatcher isbn = MockMvcResultMatchers.jsonPath("$.isbn").value("123");
		ResultMatcher name = MockMvcResultMatchers.jsonPath("$.name").value("Kürk mantolu madonna");
		ResultMatcher author = MockMvcResultMatchers.jsonPath("$.author").value("Sabahattin Ali");
		ResultMatcher year = MockMvcResultMatchers.jsonPath("$.year").value(1943);
		ResultMatcher okMatcer = MockMvcResultMatchers.status().isOk();
		mock.perform(request).andExpect(id).andExpect(isbn).andExpect(name).andExpect(author).andExpect(year).andExpect(okMatcer);
	}
}