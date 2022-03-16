package com.bookStore.bookStore.bookk;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class bookServiceImpl implements BookService {
	
	@Autowired
	BookRepository bookRepository;

	@Override
	public Page<Book> findAllBook(String name, Pageable pageable) {
		return bookRepository.findByName(name, pageable);
	}
	
	
	@Override
	public Book addBook(Book book) {
		Optional<Book> bookToAdd = bookRepository.findByName(book.getName());
		if (bookToAdd.isPresent()) {
			throw new IllegalStateException("ce livre existe deja");
		}
		return bookRepository.save(book);
	}

	@Override
	public Book updateBook(Integer id, Book book) {
		Book bookToUpdate = bookRepository.findById(id).orElse(null);
		if (bookToUpdate == null) throw new IllegalStateException("Aucun niveau trouvé pour ce livre ");
		
		Book bookByName = bookRepository.findByName(book.getName()).orElse(null);
		if (bookByName != null) {
			
			if (bookToUpdate.getId() != bookByName.getId()) {
				throw new IllegalStateException("le nom du livre existe deja");
			}
		}
		BeanUtils.copyProperties(book, bookToUpdate, "id");
		bookRepository.save(bookToUpdate);
		return bookToUpdate;
	}



	@Override
	public void deleteBook(Integer id) {
		Book bookToDelete = bookRepository.findById(id).orElse(null);
		
		if (bookToDelete == null) throw new IllegalStateException("ce livre n'existe pas");
		
		  bookRepository.deleteById(bookToDelete.getId());;
	}


	@Override
	public Book getBookDetails(Integer id) {
		return bookRepository.getBookDetails(id);
	}


	@Override
	public List<Book> getBookSimpleList() {
		return bookRepository.findAllBook();
	}



	


}
