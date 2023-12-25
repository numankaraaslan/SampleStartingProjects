package com.example.springjdbcthymeleaf.model;

//CREATE TABLE IF NOT EXISTS "JDBC"."Book"
//(
//    id integer NOT NULL,
//    author character varying(255) COLLATE pg_catalog."default",
//    name character varying(255) COLLATE pg_catalog."default",
//    year integer,
//    CONSTRAINT "Book_pkey" PRIMARY KEY (id)
//)

public class Book
{
	private int id;

	public Book(String name, int year, String author)
	{
		this.name = name;
		this.year = year;
		this.author = author;
	}

	public Book(int id, String name, int year, String author)
	{
		this.id = id;
		this.name = name;
		this.year = year;
		this.author = author;
	}

	private String name;

	private int year;

	private String author;

	public Book()
	{
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

	@Override
	public String toString()
	{
		return "Book [id=" + id + ", name=" + name + ", year=" + year + ", author=" + author + "]";
	}

}
