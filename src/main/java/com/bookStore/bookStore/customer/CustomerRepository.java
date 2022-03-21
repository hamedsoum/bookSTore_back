package com.bookStore.bookStore.customer;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("SELECT c FROM Customer c WHERE c.firstName like %:firstName% AND c.lastName like %:lastName%")
	Page<Customer> findFirstName(String firstName,String lastName, Pageable pageable);
	
	Optional<Customer> findByFirstName(String firstName);
	
	Optional<Customer> findByEmail(String email);
	
	@Query("SELECT c FROM Customer c WHERE c.id =:id")
	Customer getCustomerDetail(Integer id);
	
	@Query("SELECT c FROM Customer c")
	List<Customer> getAllCustomer();
}
