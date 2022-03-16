package com.bookStore.bookStore.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@PostMapping("/post")
	Customer addNewCustomer(@RequestBody Customer customer) {
		return customerService.addNewCustomer(customer);
	}
	
	@GetMapping("/simple-list")
	List<Customer> getSiAllCustomerSimpleList(){
		return customerService.getAllCustomerSimpleList();
	}
	
	@GetMapping("/getDetails/{customerId}")
	Customer getCustomerDetails(@PathVariable("customerId") Integer id) {
		return customerService.getCutomerDetails(id);
	}
}
