package com.example.springhibernatethymeleaf.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
public class BeanFactory
{
	private final ApplicationContext applicationContext;

	public BeanFactory(ApplicationContext applicationContext)
	{
		this.applicationContext = applicationContext;
	}

	@Bean
	public ThymeleafViewResolver viewResolver()
	{
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine());
		// DO NOT set the viewnames like this !!!!!
		// viewResolver.setViewNames(new String[] { ".html", ".xhtml" });
		return viewResolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine()
	{
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		templateEngine.setEnableSpringELCompiler(true);
		return templateEngine;
	}

	@Bean
	public SpringResourceTemplateResolver templateResolver()
	{
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		templateResolver.setApplicationContext(this.applicationContext);
		templateResolver.setPrefix("/WEB-INF/templates/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setCacheable(false);
		return templateResolver;
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
		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		return hibernateProperties;
	}

	@Bean(name = "sessionFactory")
	@DependsOn(value = "datasource")
	public LocalSessionFactoryBean sessionFactory(@Autowired @Qualifier(value = "datasource") DataSource ds)
	{
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(ds);
		sessionFactory.setPackagesToScan("com.example.springhibernatethymeleaf.model");
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