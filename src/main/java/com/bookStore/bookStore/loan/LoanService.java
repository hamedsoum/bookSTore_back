package com.bookStore.bookStore.loan;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public interface LoanService {
	Page<Loan>getAllLoan(Integer book,Pageable pageable);
	
	Loan addNewLoan(LoanDto loanDto);
	
	Loan updateLoan(Integer id, LoanDto loanDto);
	
	List<Loan>loanSimpleList();
	
	Loan getLaon(Integer id);
}
 