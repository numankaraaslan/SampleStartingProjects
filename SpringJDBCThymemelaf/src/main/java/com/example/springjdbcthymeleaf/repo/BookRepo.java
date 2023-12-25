package com.example.springjdbcthymeleaf.repo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.springjdbcthymeleaf.model.Book;

@Repository
public class BookRepo
{
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	// postgresl schema is JDBC

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate)
	{
		this.jdbcTemplate = jdbcTemplate;
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	public List<Book> getAll()
	{
		return jdbcTemplate.query("select * from \"JDBC\".\"Book\" order by \"id\" asc", BeanPropertyRowMapper.newInstance(Book.class));
	}

	public boolean deleteByID(int id)
	{
		String sql = "delete from \"JDBC\".\"Book\" where \"id\" = :ID";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("ID", id);
		return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
	}

	public Book getByID(int id)
	{
		Book konu = null;
		String sql = "select * from \"JDBC\".\"Book\" where \"id\" = :ID";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("ID", id);
		konu = namedParameterJdbcTemplate.queryForObject(sql, paramMap, BeanPropertyRowMapper.newInstance(Book.class));
		return konu;
	}

	public boolean save(Book book)
	{
		String sql = "INSERT INTO \"JDBC\".\"Book\" (id, author, name, year) VALUES (:id, :author, :name, :year)";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("id", book.getId());
		paramMap.put("author", book.getAuthor());
		paramMap.put("name", book.getName());
		paramMap.put("year", book.getYear());
		return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
	}

	public List<Book> searchByName(String name)
	{
		String sql = "select * from \"JDBC\".\"Book\" where \"name\" LIKE :NAME";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("NAME", "%" + name + "%");
		return namedParameterJdbcTemplate.query(sql, paramMap, BeanPropertyRowMapper.newInstance(Book.class));
	}
}
