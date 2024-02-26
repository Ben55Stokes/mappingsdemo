package com.hglobal.demo.requestbody;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BooksDTO {
	private Integer bookId;
	private String title;
	private String isbn;
}
