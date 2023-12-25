package com.example.jakartaejb.model;

//	CREATE TABLE IF NOT EXISTS "JakartaEE"."Book"
//	(
//	    id integer NOT NULL,
//	    author character varying(255,
//	    name character varying(255),
//	    year integer,
//	    CONSTRAINT "Book_pkey" PRIMARY KEY (id)
//	)

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

}