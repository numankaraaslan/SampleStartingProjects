package com.example.jakartajparestjwt.config;

import java.util.HashMap;
import java.util.Map;

import com.zaxxer.hikari.HikariDataSource;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@ApplicationScoped
public class DBConfig
{
	private EntityManagerFactory entityManagerFactory;

	public EntityManager getEntityManager()
	{
		return entityManagerFactory.createEntityManager();
	}

	@PostConstruct
	public void init()
	{
		entityManagerFactory = Persistence.createEntityManagerFactory("myPersistenceUnit", getJpaProperties());
	}

	private Map<String, Object> getJpaProperties()
	{
		Map<String, Object> properties = new HashMap<>();
		properties.put("jakarta.persistence.jdbc.driver", "org.postgresql.Driver");
		properties.put("jakarta.persistence.jdbc.url", "jdbc:postgresql://localhost:5432/EJB");
		properties.put("jakarta.persistence.jdbc.user", "postgres");
		properties.put("jakarta.persistence.jdbc.password", "123456789");
		properties.put("hibernate.hbm2ddl.auto", "create");
		properties.put("hibernate.default_schema", "\"JakartaEE\"");
		properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.format_sql", "true");
		properties.put("hibernate.highlight_sql", "true");
		properties.put("hibernate.transaction.jta.platform", "org.hibernate.service.jta.platform.internal.SunOneJtaPlatform");
		return properties;
	}
}
