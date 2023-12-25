package com.example.springjdbcthymeleaf.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import com.example.springjdbcthymeleaf.Constants;

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

	@Bean(name = "myds")
	public DataSource getDatasource()
	{
		DriverManagerDataSource ds = new DriverManagerDataSource(Constants.URL, Constants.USER, Constants.PASSWORD);
		ds.setDriverClassName("org.postgresql.Driver");
		return ds;
	}

	@Bean()
	@DependsOn(value = "myds")
	public JdbcTemplate createjdbctemplate(@Qualifier(value = "myds") DataSource ds)
	{
		return new JdbcTemplate(ds);
	}

	@Bean
	@DependsOn(value = "myds")
	public NamedParameterJdbcTemplate mynamedparameterjdbctemplate(@Qualifier(value = "myds") DataSource ds)
	{
		return new NamedParameterJdbcTemplate(ds);
	}

	@Bean(value = "txManager")
	@DependsOn(value = "myds")
	public DataSourceTransactionManager getTransacitonManager(@Autowired @Qualifier(value = "myds") DataSource ds)
	{
		return new DataSourceTransactionManager(ds);
	}
}