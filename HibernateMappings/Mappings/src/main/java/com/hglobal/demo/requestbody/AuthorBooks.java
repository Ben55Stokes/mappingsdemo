package com.hglobal.demo.requestbody;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorBooks {

	private Integer authorId;
	private String name;
	private String email;
	private List<BooksDTO> books;
}
