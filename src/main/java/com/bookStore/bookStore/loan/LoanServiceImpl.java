package com.bookStore.bookStore.loan;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
		LocalDate current_date = LocalDate.now();
		Integer lastLoanId = loanRepository.getlastLoanId();
		if (lastLoanId == null) {
			addLoan.setLoanNumber("LOAN1"+"-"+ current_date.getYear());
		}else {
			lastLoanId ++;
			addLoan.setLoanNumber("LOAN" + lastLoanId +"-"+ current_date.getYear());
		}
//		addLoan.setLoanNumber("loan" + 1);
		addLoan.setBook(book);
		addLoan.setCustomer(customer);
		loanRepository.save(addLoan);
		Date lastLoanDate = loanRepository.lastReturnBookDate(book.getId());
		System.out.println(lastLoanDate);
		if (lastLoanDate != null) {
			book.setLastLoanReturnDate(lastLoanDate);
			bookRepository.save(book);
		}
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
	public Page<Loan> getAllLoan(Integer book,Integer customer, String loanNumber, Pageable pageable) {
		if (ObjectUtils.isEmpty(book) && ObjectUtils.isEmpty(customer) && StringUtils.isEmpty(loanNumber) ) {
			
			return loanRepository.getAllLoan(pageable);
			
		}else if (ObjectUtils.isNotEmpty(book) && ObjectUtils.isEmpty(customer) && StringUtils.isEmpty(loanNumber.trim())) {
			
			return loanRepository.getAllLoanByBook(book, pageable);
			
		}else if (ObjectUtils.isEmpty(book) && ObjectUtils.isNotEmpty(customer) && StringUtils.isEmpty(loanNumber.trim())) {
			
			return loanRepository.getAllLoanByCustomer(customer, pageable);
			
		}else if (ObjectUtils.isEmpty(book) && ObjectUtils.isEmpty(customer) && StringUtils.isNotEmpty(loanNumber.trim())) {
			
			return loanRepository.getAllLoanByLoanNumber(loanNumber, pageable);
			
		}else if (ObjectUtils.isNotEmpty(book) && ObjectUtils.isNotEmpty(customer) && StringUtils.isEmpty(loanNumber.trim())) {
			
			return loanRepository.getAllLoanByBookAndCustomer(book,customer, pageable);
			
		}else if (ObjectUtils.isNotEmpty(book) && ObjectUtils.isEmpty(customer) && StringUtils.isNotEmpty(loanNumber.trim())) {
			
			return loanRepository.getAllLoanByBookAndLoanNumber(book,loanNumber, pageable);
			
		}else if (ObjectUtils.isEmpty(book) && ObjectUtils.isNotEmpty(customer) && StringUtils.isNotEmpty(loanNumber.trim())) {
			
			return loanRepository.getAllLoanByCustomerAndLoanNumber(customer,loanNumber, pageable);
			
		}
		else {
			
			return loanRepository.getAllLoanByBookAndCustomerAndloanNumber(book,customer,loanNumber,pageable);
		}
	}

	@Override
	public Loan getLaon(Integer id) {
		return loanRepository.getDetail(id);
	}

}
