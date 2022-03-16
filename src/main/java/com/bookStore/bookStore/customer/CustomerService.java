package com.bookStore.bookStore.customer;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {

	Page<Customer>getAllCustomer(String firstName, Pageable pageable);
	
	Customer addNewCustomer(Customer customer);
	
	Customer updateCustomer(Integer id, Customer customer);
	
	void deleteCustomer(Integer id);
	
	Customer getCutomerDetails(Integer id);
	
	List<Customer>getAllCustomerSimpleList();
}
