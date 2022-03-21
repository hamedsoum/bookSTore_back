package com.bookStore.bookStore.customer;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository;
	
	@Override
	public Page<Customer> getAllCustomer(String firstName,String lastName, Pageable pageable) {
		return customerRepository.findFirstName(firstName,lastName, pageable);
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
		Customer customerToUpdate = customerRepository.findById(id).orElse(null);
		if (customerToUpdate == null) throw new IllegalStateException("aucun client trouve pour cet identifiant");
	
		Customer customerByEmail = customerRepository.findByEmail(customer.getEmail()).orElse(null);
		if (customerByEmail != null) {
			if (customerToUpdate.getId() != customerByEmail.getId()) {
				throw new IllegalStateException("l'email existe deja");
			}
		}
		BeanUtils.copyProperties(customer, customerToUpdate,"id");
		customerRepository.save(customerToUpdate);
		return customerToUpdate;
	}

	@Override
	public void deleteCustomer(Integer id) {
		Customer customerToDelete = customerRepository.findById(id).orElse(null);
		if(customerToDelete == null) throw new IllegalStateException("aucun client trouve pour ce identifiant");
		customerRepository.deleteById(id);
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
