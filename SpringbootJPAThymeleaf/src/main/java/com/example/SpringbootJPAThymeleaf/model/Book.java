package com.example.SpringbootJPAThymeleaf.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//schema is "jpa", see applicaiton.properties
@Entity
@Table
public class Book
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private String name;

	@Column
	private int year;

	@Column
	private String author;

	public Book()
	{
	}

	public Book(int id, String name, int year, String author)
	{
		this.id = id;
		this.name = name;
		this.year = year;
		this.author = author;
	}

	public Book(String name, int year, String author)
	{
		this.name = name;
		this.year = year;
		this.author = author;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getYear()
	{
		return year;
	}

	public void setYear(int year)
	{
		this.year = year;
	}

	public String getAuthor()
	{
		return author;
	}

	public void setAuthor(String author)
	{
		this.author = author;
	}

}
