package com.example.springhibernatethymeleaf.repo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaRoot;
import org.springframework.stereotype.Repository;

import com.example.springhibernatethymeleaf.model.Book;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class BookRepo
{
	// either em or session, choose wisely
	private SessionFactory sessionFactory;

	@PersistenceContext
	EntityManager em;

	public BookRepo(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}

	public List<Book> getBooks()
	{
		Session session = sessionFactory.getCurrentSession();
		JpaCriteriaQuery<Book> query = session.getCriteriaBuilder().createQuery(Book.class);
		query.from(Book.class);
		List<Book> list = session.createQuery(query).getResultList();
		return list;
	}

	public void deleteById(int id)
	{
		em.remove(em.find(Book.class, id));
		em.flush();
	}

	public Book findById(int id)
	{
		Session session = sessionFactory.getCurrentSession();
		JpaCriteriaQuery<Book> query = session.getCriteriaBuilder().createQuery(Book.class);
		JpaRoot<Book> entity = query.from(Book.class);
		query.where(session.getCriteriaBuilder().equal(entity.get("id"), id));
		Book result = session.createQuery(query).getSingleResult();
		return result;
	}

	public void save(Book book)
	{
		Session session = sessionFactory.getCurrentSession();
		session.persist(book);
	}
}
