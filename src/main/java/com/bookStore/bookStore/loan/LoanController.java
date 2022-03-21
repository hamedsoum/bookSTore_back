package com.bookStore.bookStore.loan;

import static org.springframework.http.HttpStatus.OK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/loan")
public class LoanController {
	
	@Autowired
	LoanService loanService;
	
	@PostMapping("/post")
	ResponseEntity<Loan> addNewLoan(@RequestBody LoanDto loanDto){
		Loan loanTOadd = loanService.addNewLoan(loanDto);
		return new ResponseEntity<Loan>(loanTOadd, HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	Loan updateLoan(@PathVariable("id") Integer id, @RequestBody LoanDto loanDto) {
		return loanService.updateLoan(id, loanDto);
	}
	
	@GetMapping("/simple-list")
	List<Loan>loanSimpleList(){
		return loanService.loanSimpleList();
	}
	
	@GetMapping("/list")
	public ResponseEntity<Map<String, Object>> getAllLoan(
			@RequestParam(required = false, defaultValue = "")  Integer book,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,desc") String[] sort
			){
		List<Loan> loanList = new ArrayList<Loan>();
		
		Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;
		
		Pageable paging = PageRequest.of(page, size, Sort.by(dir, sort[0]));
		
		Page<Loan> pageLoan;
		
		pageLoan = loanService.getAllLoan(book, paging);
		
		loanList = pageLoan.getContent();
		
		Map<String, Object> response = new HashMap<>();
		response.put("items", loanList);
		response.put("currentPage", pageLoan.getNumber());
		response.put("totalItems", pageLoan.getTotalElements());
		response.put("totalPages", pageLoan.getTotalPages());
		response.put("size", pageLoan.getSize());
		response.put("first", pageLoan.isFirst());
		response.put("last", pageLoan.isLast());
		response.put("empty", pageLoan.isEmpty());
		return new ResponseEntity<>(response, OK);
	}
	

}
