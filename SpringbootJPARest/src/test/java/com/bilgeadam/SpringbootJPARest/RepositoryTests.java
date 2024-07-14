package com.bilgeadam.SpringbootJPARest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.bilgeadam.SpringbootJPARest.model.Book;
import com.bilgeadam.SpringbootJPARest.repo.BookRepo;

@DataJpaTest
@Import(BookRepo.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoryTests
{
	@Autowired
	public BookRepo bookRepo;

	@Test
	@org.springframework.test.annotation.Rollback(value = false)
	public void getbyid()
	{
		// db den gelene actual dedim
		Book actual = bookRepo.findById(1L).get();
		// olması gerekene expected dedim
		Book expected = new Book(1, "Kürk mantolu madonna", "123", 1943, "Sabahattin Ali");
		// sırası karışmasın, önce expected sonra actual
		// junit Assertions
		Assertions.assertEquals(expected, actual);
	}
}