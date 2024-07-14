package com.bilgeadam.SpringbootJPARest;

import java.util.HashMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.RestTemplate;

import com.bilgeadam.SpringbootJPARest.model.Book;

// burada DEFINED_PORT vermezsem uygualama başka bir porttan başlıyor random
// dirtiescontext ile her test bitince context temizlenir ve spring boot kapatılır
@DirtiesContext
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DisplayName(value = "Bütün entegrasyon testleri")
// test sırası önemli ise metodların tepesinde @org.junit.jupiter.api.Order yazarak sıra verebilmek için
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class SpringBootTests
{
	@Test
	@DisplayName(value = "book getbyid testi") // dikkat junit 5 ve sonrası için
	@Order(value = 1)
	void restteamplatetest()
	{
		RestTemplate template = new RestTemplate();
		String url = "http://localhost:8080/book/{id}";
		HashMap<String, Object> uriVariables = new HashMap<String, Object>();
		uriVariables.put("id", 1);
		ResponseEntity<Book> response = template.getForEntity(url, Book.class, uriVariables);
		// junit assertions olacak junit 5 ile gelmiş bu da
		Book originalBook = new Book(1, "Kürk mantolu madonna", "123", 1943, "Sabahattin Ali");
		Assertions.assertEquals(response.getBody(), originalBook, "gelen book datası yanlış !!!");
	}
}