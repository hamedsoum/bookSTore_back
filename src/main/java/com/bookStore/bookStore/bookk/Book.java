package com.bookStore.bookStore.bookk;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false)
	private Integer id;
	private String name;
	private String author;
	private Integer qty;
	private Integer loanQty;
	private Boolean available;
	
	@Column(columnDefinition = "MEDIUMBLOB")
	private String imageUrl;
	
//	@Transient
//	String getLocalImagePath() {
//		
//		return "/book-images/" + id + "/" + imageUrl;
//	}
}

