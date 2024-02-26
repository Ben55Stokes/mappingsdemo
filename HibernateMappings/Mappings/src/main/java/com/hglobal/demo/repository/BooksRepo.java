package com.hglobal.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hglobal.demo.entity.Books;

public interface BooksRepo extends JpaRepository<Books, Integer> {
	
	@Query(value="select b.* from author a I join book b on a.id=b.author_id where a.id=:id", nativeQuery = true)
	List<Books> getAllBooks(@Param("id") Integer id);
}
