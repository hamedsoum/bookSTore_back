package com.bookStore.bookStore.bookk;

import static org.springframework.http.HttpStatus.OK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/book")
public class BookController {
	
	@Autowired
	BookService bookService;
	
	@PostMapping("/post")
	 Book addNewBook(@RequestBody Book book ) {
		return bookService.addBook(book);
	}
		
	@GetMapping("/list")
	ResponseEntity<Map<String, Object>> getAllBook(
			@RequestParam(required = false, defaultValue = "") String name,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,desc") String[] sort
			){
		
		List<Book> bookList = new ArrayList<Book>();
		
		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;
		
		Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));
		
		Page<Book> pageBook;
		
		pageBook = bookService.findAllBook(name, paging);
		
		bookList = pageBook.getContent();
		
		Map<String, Object> response = new HashMap<>();
		response.put("items", bookList);
		response.put("currentPage", pageBook.getNumber() );
		response.put("totalItems", pageBook.getTotalElements());
		response.put("totalPages", pageBook.getTotalPages());
		response.put("size", pageBook.getSize());
		response.put("first", pageBook.isFirst());
		response.put("last", pageBook.isLast());
		response.put("empty", pageBook.isEmpty());
		
		return new ResponseEntity<>(response, OK);
	}
	
	@DeleteMapping("/delete/{bookId}")
	void deleteBook(@PathVariable("bookId") Integer id) {
		bookService.deleteBook(id);
	}
	
	@PutMapping("/update/{bookId}")
	Book updateBook(@PathVariable("bookId") Integer id, @RequestBody Book book) {
		return bookService.updateBook(id, book);
	}
	
	@GetMapping("/getDetails/{bookId}")
	Book getBookDetails(@PathVariable("bookId") Integer id) {
		return bookService.getBookDetails(id);
	}
	
	@GetMapping("/simple-list")
	List<Book> getAllBookSimpleList(){
		return bookService.getBookSimpleList();
	}
	
}
