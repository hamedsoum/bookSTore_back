package com.bookStore.bookStore.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository;
	
	@Override
	public Page<Customer> getAllCustomer(String firstName, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer addNewCustomer(Customer customer) {
		Customer customerToAdd = customerRepository.findByEmail(customer.getEmail()).orElse(null);
		if (customerToAdd != null) throw new  IllegalStateException("l'email existe deja");
		
		customerRepository.save(customer);
		return customer;
	}
 
	@Override
	public Customer updateCustomer(Integer id, Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteCustomer(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Customer getCutomerDetails(Integer id) {
		return customerRepository.getCustomerDetail(id);
	}

	@Override
	public List<Customer> getAllCustomerSimpleList() {
		return customerRepository.getAllCustomer();
	}

}
