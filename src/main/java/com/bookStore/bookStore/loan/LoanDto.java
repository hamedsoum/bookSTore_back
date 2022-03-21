package com.bookStore.bookStore.loan;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanDto {

	private Integer id;
	private Integer idBook;
	private Integer idCustomer;
	private Date releaseDate;
	private Date returnDate;
}
