package com.bookStore.bookStore.customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	
	@GetMapping("/list")
	@ApiOperation("liste pagine de tous les clients dans le systeme")
	ResponseEntity<Map<String, Object>>getAllCustomer(
			@RequestParam(required = false, defaultValue ="") String firstName,
			@RequestParam(required = false, defaultValue ="") String lastName,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id, desc") String[] sort
			){
		
		List<Customer> bookList = new ArrayList<Customer>();
		
		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;
		
		Pageable pagging = PageRequest.of(page, size, Sort.by(dir, sort[0]));
		
		Page<Customer> pageCustomer;
		
		pageCustomer = customerService.getAllCustomer(firstName,lastName, pagging);
		
		bookList = pageCustomer.getContent();
		
		Map<String, Object> response = new HashMap<>();
		response.put("items", bookList);
		response.put("currentPage", pageCustomer.getNumber());
		response.put("totalPages", pageCustomer.getTotalElements());
		response.put("totalPages", pageCustomer.getTotalPages());
		response.put("size", pageCustomer.getSize());
		response.put("first", pageCustomer.isFirst());
		response.put("last", pageCustomer.isLast());
		response.put("empty", pageCustomer.isEmpty());

		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/post")
	@ApiOperation("ajouter un client dans le systeme")
	Customer addNewCustomer(@RequestBody Customer customer) {
		return customerService.addNewCustomer(customer);
	}
	
	@PutMapping("/update/{customerId}")
	@ApiOperation("modifier un client dans le systeme")
	Customer updateCustomer(@PathVariable("customerId") Integer id, @RequestBody Customer customer){
		return customerService.updateCustomer(id, customer);
	}
	@GetMapping("/simple-list")
	@ApiOperation("liste simple de tous les cleints dans le systeme")
	List<Customer> getSiAllCustomerSimpleList(){
		return customerService.getAllCustomerSimpleList();
	}
	
	@GetMapping("/getDetails/{customerId}")
	@ApiOperation("details d'un client dans le systeme")
	Customer getCustomerDetails(@PathVariable("customerId") Integer id) {
		return customerService.getCutomerDetails(id);
	}
	
	@DeleteMapping("delete/{customerId}")
	@ApiOperation("supprimer un client dans le systeme")
	void deleteCustomer (@PathVariable("customerId") Integer id) {
		customerService.deleteCustomer(id);
	} 
}
