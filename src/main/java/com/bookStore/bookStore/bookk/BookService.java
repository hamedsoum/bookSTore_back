package com.bookStore.bookStore.bookk;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public interface BookService {
    Page<Book> findAllBook(String name, Pageable pageable);
	
	void deleteBook(Integer id);
	
	Book getBookDetails(Integer id);
	
	Book addBook(Book book);
	
	Book updateBook(Integer id, Book book);

	List<Book>getBookSimpleList();
}
