package com.bilgeadam.SpringbootJPARest.repo;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bilgeadam.SpringbootJPARest.model.Book;

@Repository
public interface BookRepo extends JpaRepository<Book, Long>
{
	public List<Book> searchBynameLikeIgnoreCase(String name, Sort sortOrder);

	@Query(value = "SELECT * FROM \"jpa\".\"book\" WHERE LOWER(AUTHOR) LIKE LOWER(?1) ORDER BY AUTHOR ASC", nativeQuery = true)
	public List<Book> findByAuthorNameIgnoreCase(String authorName);
}