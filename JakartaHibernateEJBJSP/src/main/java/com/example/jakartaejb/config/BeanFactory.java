package com.example.jakartaejb.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import jakarta.annotation.PostConstruct;

@jakarta.ejb.Singleton
public class BeanFactory
{
	private SessionFactory sessionFactory;

	@PostConstruct
	public void initialize()
	{
		sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	}

	public SessionFactory getSessionFactory()
	{
		return sessionFactory;
	}
}
