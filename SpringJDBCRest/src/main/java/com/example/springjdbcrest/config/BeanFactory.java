package com.example.springjdbcrest.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.example.springjdbcrest.Constants;

@Configuration
public class BeanFactory
{
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