package com.bookStore.bookStore.bookk;


import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public interface BookService {
	
    Page<Book> findAllBook(String name,Boolean availabe, Pageable pageable);
	
	void deleteBook(Integer id);
	
	Book getBookDetails(Integer id);
	
//	Book addBook(Book book); 
	public void addBook( MultipartFile file, String name, String author,Integer qty, Integer loanQty, Boolean available ) throws IOException;
	
	Book updateBook(Integer id,  MultipartFile file, String name, String author,Integer qty, Integer loanQty, Boolean available);

	List<Book>getBookSimpleList();
}
