package com.bookStore.bookStore.loan;

import java.sql.Date;
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
	
	
	// Afficher la plus proche date de retour d’un livre emprunté
	@Query(value= "SELECT `return_date` FROM `loan` as l WHERE l.id_book =:idBook ORDER BY return_date LIMIT 1", nativeQuery = true)
	Date lastReturnBookDate(Integer idBook);
	//SELECT return_date FROM loan WHERE id_book = 1 ORDER BY return_date ASC LIMIT 1
	
	String jql ="Select f from Foo as f order by f.id";

	@Query(value = "SELECT id FROM `loan` ORDER BY id DESC LIMIT 1;", nativeQuery = true)
	Integer getlastLoanId();
	
}
