package com.example.jakartaejb.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.example.jakartaejb.Constants;
import com.example.jakartaejb.model.Book;

@jakarta.ejb.Singleton
public class BookRepo
{
	// postgresql schema is JakartaEE

	public boolean save(Book book) throws SQLException
	{
		boolean result = false;
		Connection con = Constants.getConnection();
		String sql = "INSERT INTO \"JakartaEE\".\"Book\"(id, author, name, year) VALUES (?, ?, ?, ?)";
		PreparedStatement stmnt = con.prepareStatement(sql);
		stmnt.setInt(1, book.getId());
		stmnt.setString(2, book.getAuthor());
		stmnt.setString(3, book.getName());
		stmnt.setInt(4, book.getYear());
		result = stmnt.executeUpdate() == 1;
		stmnt.close();
		con.close();
		return result;
	}

	public boolean deleteById(long id) throws SQLException
	{
		Connection con = Constants.getConnection();
		String sql = "delete from \"JakartaEE\".\"Book\" where \"id\" = ?";
		PreparedStatement stmnt = con.prepareStatement(sql);
		stmnt.setLong(1, id);
		boolean result = stmnt.executeUpdate() == 1;
		stmnt.close();
		con.close();
		return result;
	}

	public Book findById(int id) throws SQLException
	{
		Connection con = Constants.getConnection();
		Book book = null;
		String sql = "select * from \"JakartaEE\".\"Book\" where \"id\" = ?";
		PreparedStatement stmnt = con.prepareStatement(sql);
		stmnt.setLong(1, id);
		ResultSet result = stmnt.executeQuery();
		while (result.next())
		{
			book = new Book(result.getInt("id"), result.getString("name"), result.getInt("year"), result.getString("author"));
		}
		result.close();
		stmnt.close();
		con.close();
		return book;
	}

	public ArrayList<Book> getBooks() throws SQLException
	{
		ArrayList<Book> list = new ArrayList<>();
		Connection con = Constants.getConnection();
		Statement stmnt = con.createStatement();
		ResultSet result = stmnt.executeQuery("select * from \"JakartaEE\".\"Book\" order by \"id\" asc");
		while (result.next())
		{
			list.add(new Book(result.getInt("id"), result.getString("name"), result.getInt("year"), result.getString("author")));
		}
		result.close();
		stmnt.close();
		con.close();
		return list;
	}
}
