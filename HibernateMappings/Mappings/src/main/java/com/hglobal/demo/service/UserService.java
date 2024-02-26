package com.hglobal.demo.service;

import com.hglobal.demo.requestbody.AuthorBooks;
import com.hglobal.demo.requestbody.SaveUserRequestBody;
import com.hglobal.demo.utility.Result;

public interface UserService {

	public Result saveUser(SaveUserRequestBody saveUserRequestBody);
	public String getUser(Integer userId);
	public Result saveAuthorAndBooks(AuthorBooks authorBooks);
	public Result getAuthorAndBooks(Integer authorId);
}
