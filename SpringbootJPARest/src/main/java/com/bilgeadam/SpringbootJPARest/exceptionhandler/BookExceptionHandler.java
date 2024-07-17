package com.bilgeadam.SpringbootJPARest.exceptionhandler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bilgeadam.SpringbootJPARest.controller.BookController;

@RestControllerAdvice(basePackageClasses = BookController.class)
public class BookExceptionHandler
{
	@ExceptionHandler(value = DataIntegrityViolationException.class)
	public String badSqlGrammerExceptionHandler(DataIntegrityViolationException e)
	{
		System.err.println("DataIntegrityViolationException yakalandı -> " + e.getMessage());
		return "Girdiğiniz dataya dikkat ediniz";
	}

	@ExceptionHandler(value = InvalidDataAccessResourceUsageException.class)
	public ResponseEntity<String> badSqlHandler(InvalidDataAccessResourceUsageException e)
	{
		System.err.println(e.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Yazılımcı kodu yanlış yazmış");
	}
}
