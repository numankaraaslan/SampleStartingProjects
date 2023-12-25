package com.example.springhibernatejsp.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

@Configuration
public class BeanFactory
{
	@Bean
	public ViewResolver viewResolver()
	{
		UrlBasedViewResolver resolver = new UrlBasedViewResolver();
		resolver.setPrefix("/jsppages/");
		resolver.setSuffix(".jsp");
		resolver.setViewClass(JstlView.class);
		return resolver;
	}

	@Bean(name = "datasource")
	public DataSource dataSource()
	{
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/Spring");
		dataSource.setUsername("postgres");
		dataSource.setPassword("YOURPASSWORD");
		return dataSource;
	}

	private final Properties hibernateProperties()
	{
		Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
		hibernateProperties.setProperty("hibernate.default_schema", "jpa");
		return hibernateProperties;
	}

	@Bean(name = "sessionFactory")
	@DependsOn(value = "datasource")
	public LocalSessionFactoryBean sessionFactory(@Autowired @Qualifier(value = "datasource") DataSource ds)
	{
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(ds);
		sessionFactory.setPackagesToScan("com.example.springhibernatejsp.model");
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}

	// this is a default name that spring expects i guess
	@Bean(name = "transactionManager")
	@DependsOn(value = { "datasource", "sessionFactory" })
	public HibernateTransactionManager getManager(@Autowired @Qualifier(value = "datasource") DataSource ds, @Autowired @Qualifier(value = "sessionFactory") LocalSessionFactoryBean sf)
	{
		return new HibernateTransactionManager(sf.getObject());
	}
}
