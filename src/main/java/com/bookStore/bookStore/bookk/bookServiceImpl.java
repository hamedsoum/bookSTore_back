package com.bookStore.bookStore.bookk;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.bookStore.bookStore.utility.FileUploadUtil;

@Service
public class bookServiceImpl implements BookService {
	
	@Autowired
	BookRepository bookRepository;

	@Override
	public Page<Book> findAllBook(String name, Boolean available, Pageable pageable) {
		return bookRepository.finAllBook(name,available,pageable);
	}
	
	
//	@Override
//	public Book addBook(Book book) {
//		Optional<Book> bookToAdd = bookRepository.findByName(book.getName());
//		if (bookToAdd.isPresent()) {
//			throw new IllegalStateException("ce livre existe deja");
//		}
//		return bookRepository.save(book);
//	}

	@Override
	public Book updateBook(Integer id, Book book) {
		Book bookToUpdate = bookRepository.findById(id).orElse(null);
		if (bookToUpdate == null) throw new IllegalStateException("Aucun livre trouvé pour ce identifiant ");
		
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
		return bookRepository.findAllBookSimpleList();
	}


	@Override
	public void addBook(MultipartFile file, String name, String author, Integer qty, Integer loanQty,
			Boolean available) throws IOException {
		Book b = new Book();
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		if (fileName.contains("..")) throw new IllegalStateException("fichier invalid");
			b.setImageUrl(file.getOriginalFilename());
//			b.setImageUrl(java.util.Base64.getEncoder().encodeToString(file.getBytes()));
			// TODO Auto-generated catch block
		
		b.setName(name);
		b.setAuthor(author);
		b.setQty(qty);
		b.setLoanQty(0);
		b.setAvailable(true);
		Book newBook = bookRepository.save(b);
		String uploadDir = "./book-images/";
		
		FileUploadUtil.saveFile(uploadDir, fileName, file);

//		FileUploadUtil.saveFile(uploadDir, fileName, file);

	}



	


}
