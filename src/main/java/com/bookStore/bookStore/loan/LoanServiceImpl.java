package com.bookStore.bookStore.loan;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.bookStore.bookStore.bookk.Book;
import com.bookStore.bookStore.bookk.BookRepository;
import com.bookStore.bookStore.customer.Customer;
import com.bookStore.bookStore.customer.CustomerRepository;

@Service
public class LoanServiceImpl implements LoanService{
	@Autowired
	LoanRepository loanRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	BookRepository bookRepository;

	@Override
	public Loan addNewLoan(LoanDto loanDto) {
		Book book = bookRepository.findById(loanDto.getIdBook()).orElse(null);
		Customer customer = customerRepository.findById(loanDto.getIdCustomer()).orElse(null);
		
		if (book == null) throw new IllegalStateException("aucun livre trouve pour cet indentifiant");
		
		if (customer == null) throw new IllegalStateException("aucun client trouve pour cet indentifiant");
		
		if (book.getQty() == book.getLoanQty() || book.getQty() == 0) {
			 throw new IllegalStateException("plus de livre disponible pour un emprunt" );
		} else {
			Integer newBookLoanQty = book.getLoanQty() + 1;
			book.setLoanQty(newBookLoanQty);
			if (book.getQty() == book.getLoanQty())book.setAvailable(false);
		}
		
		Loan addLoan = new Loan();
		BeanUtils.copyProperties(loanDto, addLoan, "id");
		addLoan.setLoanNumber("loan" + 1);
		addLoan.setBook(book);
		addLoan.setCustomer(customer);
		loanRepository.save(addLoan);
		return addLoan;		
	}

	@Override
	public Loan updateLoan(Integer id, LoanDto loanDto) {
		Loan loanToUpdate = loanRepository.findById(id).orElse(null);
		Book book = bookRepository.findById(loanDto.getIdBook()).orElse(null);
		Customer customer = customerRepository.findById(loanDto.getIdCustomer()).orElse(null);
		if (book == null) throw new IllegalStateException("aucun livre trouve pour cet indentifiant");
		if (customer == null) throw new IllegalStateException("aucun client trouve pour cet indentifiant");
		if (loanToUpdate == null) throw new IllegalStateException("aucun emprun trouve pour cet identifiant");
		
		BeanUtils.copyProperties(loanDto, loanToUpdate, "id");
		loanToUpdate.setBook(book);
		loanToUpdate.setCustomer(customer);
		loanRepository.save(loanToUpdate);
		return loanRepository.save(loanToUpdate);
	}

	@Override
	public List<Loan> loanSimpleList() {
		return loanRepository.getLoanSimpleList();
	}

	@Override
	public Page<Loan> getAllLoan(Integer book, Pageable pageable) {
		if (ObjectUtils.isEmpty(book)) {
			return loanRepository.getAllLoan(pageable);

		} else {
			return loanRepository.getAllLoanByBook(book,pageable);
		}
	}

}
