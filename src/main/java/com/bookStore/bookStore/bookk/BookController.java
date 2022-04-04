package com.bookStore.bookStore.bookk;

import static org.springframework.http.HttpStatus.OK;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping(path = "/book")
public class BookController {
	
	@Autowired
	BookService bookService;
	
	@PostMapping(value = "/post")
	@ApiOperation("ajouter un livre dans le systeme")
	
	public void addNewBook(
			@RequestParam("file") MultipartFile file,
			@RequestParam("name") String name,
			@RequestParam("author") String author,
			@RequestParam("qty") Integer qty,
			@RequestParam("loanQty") Integer loanQty,
			@RequestParam("available") Boolean available
			) throws IOException {
//		System.out.println(name);
		 bookService.addBook(file, name, author, qty, loanQty, available);
		
	}
//	 Book addNewBook(@RequestBody Book book ) {
//		return bookService.addBook(book);
//	}
		
	@GetMapping("/list")
	@ApiOperation("liste pagine de tous les livres dans le systeme")
	ResponseEntity<Map<String, Object>> getAllBook(
			@RequestParam(required = false, defaultValue = "") String name,
			@RequestParam(required = false, defaultValue = "1") Boolean available,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,desc") String[] sort
			){
		
		List<Book> bookList = new ArrayList<Book>();
		
		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;
		
		Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));
		
		Page<Book> pageBook;
		
		pageBook = bookService.findAllBook(name,available, paging);
		
		pageBook.forEach(book ->{
			String fileDowloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/book/book-image/" + book.getImageUrl()).toUriString();
//			String fileDowloadUri = "./book-images/" + book.getId() + "/" + book.getImageUrl();
			book.setImageUrl(fileDowloadUri);
		});
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
	@ApiOperation("supprimer un livre dans le systeme")
	void deleteBook(@PathVariable("bookId") Integer id) {
		bookService.deleteBook(id);
	}
	
	
	@PostMapping("/update")
	@ApiOperation("modifier un livre dans le systeme")
	public Book updateBook(
			@RequestParam("file") MultipartFile file,
			@RequestParam("id") Integer id,
			@RequestParam("name") String name,
			@RequestParam("author") String author,
			@RequestParam("qty") Integer qty,
			@RequestParam("loanQty") Integer loanQty,
			@RequestParam("available") Boolean available
			) throws IOException {
return bookService.updateBook(id,file, name, author, qty, loanQty, available);
		
	}
//	Book updateBook(@PathVariable("bookId") Integer id, @RequestBody Book book) {
//		return bookService.updateBook(id, book);
//	}
	
	@GetMapping("/getDetails/{bookId}")
	@ApiOperation("details d'un livre dans le systeme")
	Book getBookDetails(@PathVariable("bookId") Integer id) {
		Book book =  bookService.getBookDetails(id);
		String fileDowloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/book/book-image/" + book.getImageUrl()).toUriString();
//		String fileDowloadUri = "./book-images/" + book.getId() + "/" + book.getImageUrl();
		book.setImageUrl(fileDowloadUri);
		return book;
	}
	
	@ApiOperation("liste simple de tous les livres dans le systeme")
	@GetMapping("/simple-list")
	List<Book> getAllBookSimpleList(){
		return bookService.getBookSimpleList();
	}
	
	@GetMapping(path ="/book-image/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
	@ApiOperation("image d'un livre dans le systeme")
	public byte[] getServerImage(@PathVariable("fileName") String fileName) throws IOException{
		return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/Documents/workspace-spring-tool/bookStore/book-images/" +fileName));
	}
	
}
