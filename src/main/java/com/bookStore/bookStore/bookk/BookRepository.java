package com.bookStore.bookStore.bookk;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
	
	@Query("SELECT b FROM Book b ")
	Page<Book> findAllBook(Pageable pageable);
	
	@Query("SELECT b FROM Book b WHERE b.available =:available AND b.name like %:name%")
	Page<Book> findAllBookByAvailableAndName(String name,Boolean available, Pageable pageable);
	
	@Query("SELECT b FROM Book b WHERE b.name like %:name%")
	Page<Book> findAllBookByName(String name, Pageable pageable);
	
	@Query("SELECT b FROM Book b Where b.id =:id")
	Book getBookDetails(Integer id);
	
	Optional<Book> findByName(String name);
	
	@Query("SELECT b FROM Book b")
	List<Book>findAllBookSimpleList();
}
