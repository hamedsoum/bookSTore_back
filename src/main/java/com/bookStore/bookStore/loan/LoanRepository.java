package com.bookStore.bookStore.loan;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
	

	@Query("SELECT l FROM Loan l ")
	Page<Loan>getAllLoan(Pageable pageable);
	
	@Query("SELECT l FROM Loan l WHERE l.book.id =:book")
	Page<Loan>getAllLoanByBook(Integer book,Pageable pageable);
	
	@Query("SELECT l FROM Loan l WHERE l.id =:id")
	Loan getDetail(Integer id);
	
	@Query(value= "SELECT * FROM `loan` as l WHERE l.id_book =:idBook", nativeQuery = true)
	Loan getLoanByIdBook(Integer idBook);
	
	
	@Query("DELETE FROM Loan l WHERE l.id =:id")
	void deleteLoan(Integer id);
	
	@Query("SELECT l FROM Loan l")
	List<Loan>getLoanSimpleList();
	
}
