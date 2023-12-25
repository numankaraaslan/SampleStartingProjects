package com.example.SpringbootJPAThymeleaf.repo;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SpringbootJPAThymeleaf.model.Book;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer>
{
	public List<Book> searchBynameLike(String name, Sort sortOrder);
}
