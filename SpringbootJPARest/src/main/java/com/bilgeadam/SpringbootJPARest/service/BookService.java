package com.bilgeadam.SpringbootJPARest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.bilgeadam.SpringbootJPARest.model.Book;
import com.bilgeadam.SpringbootJPARest.repo.BookRepo;

@Service
public class BookService
{
	@Autowired
	public BookRepo bookRepo;

	public List<Book> findByNameLike(String name, Sort sortOrder)
	{
		return bookRepo.searchBynameLikeIgnoreCase("%" + name + "%", sortOrder);
	}

	public List<Book> findByAuthorName(String authorName)
	{
		return bookRepo.findByAuthorNameIgnoreCase("%" + authorName + "%");
	}

	public Book findById(long id)
	{
		return bookRepo.findById(id).orElse(null);
	}

	public Book save(Book newBook)
	{
		return bookRepo.save(newBook);
	}

	public void deleteByID(long id)
	{
		bookRepo.deleteById(id);
	}

	public List<Book> findAll()
	{
		return bookRepo.findAll(Sort.by(Order.asc("id")));
	}
}