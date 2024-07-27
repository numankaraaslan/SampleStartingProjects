package com.example.jakartajparestjwt.repo;

import java.util.List;

import com.example.jakartajparestjwt.config.DBConfig;
import com.example.jakartajparestjwt.model.Book;

import jakarta.ejb.Singleton;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Singleton
public class BookRepository
{
	@Inject
	private DBConfig config;

	public List<Book> getBooks()
	{
		EntityManager em = config.getEntityManager();
		TypedQuery<Book> query = em.createQuery("SELECT e FROM " + Book.class.getName() + " e", Book.class);
		List<Book> list = query.getResultList();
		em.close();
		return list;

	}

	public Book save(Book book)
	{
		EntityManager em = config.getEntityManager();
		em.getTransaction().begin();
		em.persist(book);
		em.flush();
		em.getTransaction().commit();
		em.close();
		return book;
	}

	public Book getById(long id)
	{
		EntityManager em = config.getEntityManager();
		Book book = em.find(Book.class, id);
		em.close();
		return book;
	}
}