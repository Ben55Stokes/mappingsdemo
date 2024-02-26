package com.hglobal.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hglobal.demo.requestbody.AuthorBooks;
import com.hglobal.demo.requestbody.SaveUserRequestBody;
import com.hglobal.demo.service.UserServiceImpl;
import com.hglobal.demo.utility.Result;


@RestController
public class UserController {
	@Autowired
	UserServiceImpl userServiceImpl;

	/*
	 * one to one mapping save and update
	 */
	@PostMapping("/save-user")
	public Result saveUser(@RequestBody SaveUserRequestBody saveUserRequestBody) {
		return userServiceImpl.saveUser(saveUserRequestBody);
	}
	
	/*
	 * one to one mapping get
	 */
	@GetMapping("/get-user-by-id")
	public String getMethodName(@RequestParam("userId") Integer userId) {
		return userServiceImpl.getUser(userId);
	}
	
	@PostMapping("/save-authors-books")
	public Result saveAuthorAndBooks(@RequestBody AuthorBooks authorBooks) {
		return userServiceImpl.saveAuthorAndBooks(authorBooks);
	}
	
	@GetMapping("/get-authors-books")
	public Result getAuthorBooks(@RequestParam("authorId") Integer authorId) {
		return userServiceImpl.getAuthorAndBooks(authorId);
	}
}
