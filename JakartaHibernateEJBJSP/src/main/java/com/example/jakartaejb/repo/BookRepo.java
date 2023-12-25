package com.example.jakartaejb.repo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaRoot;

import com.example.jakartaejb.config.BeanFactory;
import com.example.jakartaejb.model.Book;

@jakarta.ejb.Singleton
public class BookRepo
{
	private BeanFactory factory;

	@jakarta.inject.Inject
	public void setFactory(BeanFactory factory)
	{
		this.factory = factory;
	}

	public List<Book> getBooks()
	{
		Session session = this.factory.getSessionFactory().getCurrentSession();
		session.getTransaction().begin();
		JpaCriteriaQuery<Book> query = session.getCriteriaBuilder().createQuery(Book.class);
		query.from(Book.class);
		List<Book> list = session.createQuery(query).getResultList();
		session.getTransaction().commit();
		return list;
	}

	public Book findById(int id)
	{
		Session session = this.factory.getSessionFactory().getCurrentSession();
		session.getTransaction().begin();
		JpaCriteriaQuery<Book> query = session.getCriteriaBuilder().createQuery(Book.class);
		JpaRoot<Book> entity = query.from(Book.class);
		query.where(session.getCriteriaBuilder().equal(entity.get("id"), id));
		Book result = session.createQuery(query).getSingleResult();
		session.getTransaction().commit();
		return result;
	}

	public void save(Book book)
	{
		Session session = this.factory.getSessionFactory().getCurrentSession();
		session.getTransaction().begin();
		session.persist(book);
		session.getTransaction().commit();
	}
}
