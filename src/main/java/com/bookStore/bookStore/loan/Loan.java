package com.bookStore.bookStore.loan;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.bookStore.bookStore.bookk.Book;
import com.bookStore.bookStore.customer.Customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Loan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "id_book")
	private Book book;
	
	@ManyToOne
	@JoinColumn(name = "id_customer")
	private Customer customer;
	
	private Date releaseDate;
	
	private Date returnDate;
	
	private String loanNumber;
}
