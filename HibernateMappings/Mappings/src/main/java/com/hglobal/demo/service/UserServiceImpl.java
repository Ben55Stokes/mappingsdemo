package com.hglobal.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hglobal.demo.entity.AuthorEntity;
import com.hglobal.demo.entity.Books;
import com.hglobal.demo.entity.User;
import com.hglobal.demo.entity.UserProfile;
import com.hglobal.demo.repository.AuthorRepository;
import com.hglobal.demo.repository.BooksRepo;
import com.hglobal.demo.repository.UserRepo;
import com.hglobal.demo.requestbody.AuthorBooks;
import com.hglobal.demo.requestbody.BooksDTO;
import com.hglobal.demo.requestbody.SaveUserRequestBody;
import com.hglobal.demo.utility.Constants;
import com.hglobal.demo.utility.CustomException;
import com.hglobal.demo.utility.Response;
import com.hglobal.demo.utility.Result;
import com.hglobal.demo.utility.Utility;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepo repository;
	
	@Autowired
	AuthorRepository authorRepo;
	
	@Autowired
	BooksRepo booksRepository;

	@Override
	public Result saveUser(SaveUserRequestBody saveUserRequestBody) {
		Result result = null;
		try {
			if (saveUserRequestBody.getUserId() == null
					&& saveUserRequestBody.getSaveUserProfileRequestBody().getUserProfileId() == null) {
				List<String> userEmailIds = repository.getEmailID(saveUserRequestBody.getEmailID());
				if (userEmailIds.isEmpty()) {
					User user = saveHelper(saveUserRequestBody);
					if (user != null) {
						result = new Result(HttpStatus.OK.value(), Constants.ADDED_RECORDS);
					} else {
						result = new Result(HttpStatus.NOT_FOUND.value(), Constants.NO_RECORDS_FOUND);
					}
				} else {
					result = new Result(HttpStatus.BAD_REQUEST.value(), Constants.EMAIL_EXISTS);
				}
			} else {
				User user = updateUser(saveUserRequestBody);
				if (user != null) {
					result = new Result(HttpStatus.OK.value(), Constants.UPDATED_RECORDS);
				} else {
					result = new Result(HttpStatus.NOT_FOUND.value(), Constants.NO_RECORDS_FOUND);
				}
			}
		} catch (Exception e) {
			log.error("Error in saveUser");
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		}
		return result;
	}

	public User saveHelper(SaveUserRequestBody saveUserRequestBody) {
		try {
			User user = new User();
			UserProfile userProfile = new UserProfile();
			user.setEmailId(saveUserRequestBody.getEmailID());
			user.setUserName(saveUserRequestBody.getEmailID());
			user.setPasswordHash(Utility.encryptPassword(saveUserRequestBody.getPassword()));
			userProfile.setDateOfBirth(Utility.covertDatetoDBFormat(
					saveUserRequestBody.getSaveUserProfileRequestBody().getDateOfBirth(), Constants.UI_TIME_FORMAT));
			userProfile.setFullName(saveUserRequestBody.getSaveUserProfileRequestBody().getFirstName()
					+ Constants.EMPTY_SPACE + saveUserRequestBody.getSaveUserProfileRequestBody().getLastName());
			userProfile.setGender(saveUserRequestBody.getSaveUserProfileRequestBody().getGender());
			// child table.setParenttable
			userProfile.setUser(user);
			// parent table.setChildtable
			user.setUserProfile(userProfile);
			return repository.save(user);
		} catch (Exception e) {
			log.error("error inside saveHelper");
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		}
	}

	public User updateUser(SaveUserRequestBody saveUserRequestBody) {
		try {
			User user = new User();
			UserProfile userProfile = new UserProfile();
			user.setUserId(saveUserRequestBody.getUserId());
			user.setEmailId(saveUserRequestBody.getEmailID());
			user.setUserName(saveUserRequestBody.getEmailID());
			user.setPasswordHash(Utility.encryptPassword(saveUserRequestBody.getPassword()));
			userProfile.setProfileId(saveUserRequestBody.getSaveUserProfileRequestBody().getUserProfileId());
			userProfile.setDateOfBirth(Utility.covertDatetoDBFormat(
					saveUserRequestBody.getSaveUserProfileRequestBody().getDateOfBirth(), Constants.UI_TIME_FORMAT));
			userProfile.setFullName(saveUserRequestBody.getSaveUserProfileRequestBody().getFirstName()
					+ Constants.EMPTY_SPACE + saveUserRequestBody.getSaveUserProfileRequestBody().getLastName());
			userProfile.setGender(saveUserRequestBody.getSaveUserProfileRequestBody().getGender());
			// child table.setParenttable
			userProfile.setUser(user);
			// parent table.setChildtable
			user.setUserProfile(userProfile);
			return repository.save(user);
		} catch (Exception e) {
			log.error("error inside saveHelper");
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		}
	}

	public String getUser(Integer userId) {
		Optional<User> user= repository.findById(userId);
		String emailId= user.map(User :: getUserName).orElse(null);
		return emailId!=null? emailId: Constants.NO_RECORDS_FOUND;
	}

	public Result saveAuthorAndBooks(AuthorBooks authorBooks) {
		Result result = null;
		try {
			if (authorBooks.getAuthorId() == null) {
				AuthorEntity authorEntity = authorSaveHelper(authorBooks);
				if (authorEntity != null) {
					result = new Result(HttpStatus.OK.value(), Constants.ADDED_RECORDS);
				} else {
					result = new Result(HttpStatus.NOT_FOUND.value(), Constants.NO_RECORDS_FOUND);
				}
			}
		} catch (Exception e) {
			log.error("error in saveAuthorAndBooks");
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		}
		return result;
	}
	
	
	public AuthorEntity authorSaveHelper(AuthorBooks authorBooks) {
		try {
			AuthorEntity author = new AuthorEntity();
			List<Books> books = new ArrayList<>();
			author.setName(authorBooks.getName());
			author.setEmaiId(authorBooks.getEmail());
			//save parent first other wise duplicate key issues may come
			AuthorEntity authModel= authorRepo.save(author);
			for (BooksDTO booksDTO : authorBooks.getBooks()) {
				Books book = new Books();
				book.setTitle(booksDTO.getTitle());
				book.setIsbn(booksDTO.getIsbn());
				book.setAuthorID(authModel);
				books.add(book);
			}
			booksRepository.saveAll(books);
			return authModel;
		}catch (Exception e) {
			log.error("error in authorSaveHelper");
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		}
	}

	public Result getAuthorAndBooks(Integer authorId) {
		Response response= new Response();
		try {
			AuthorEntity authorEntity= authorRepo.findById(authorId).get();
			if(authorEntity==null) {
				response.setStatusCode(HttpStatus.NOT_FOUND.value());
				response.setMessage(Constants.NO_RECORDS_FOUND);
			}else {
				AuthorBooks authorBooks= new AuthorBooks();
				authorBooks.setAuthorId(authorEntity.getId());
				authorBooks.setEmail(authorEntity.getEmaiId());
				authorBooks.setName(authorEntity.getName());
				List<BooksDTO> books = new ArrayList<>();
				List<Books>bOOKList= booksRepository.getAllBooks(authorId); // comment this call and return authorEntity  to check what is n+1 problem 
				for (Books book : bOOKList) {
					BooksDTO booksDTO= new BooksDTO();
					booksDTO.setTitle(book.getTitle());
					booksDTO.setIsbn(book.getIsbn());
					books.add(booksDTO);
				}
				authorBooks.setBooks(books);
				response.setStatusCode(HttpStatus.OK.value());
				response.setMessage(Constants.FETCHING_RECORDS);
				response.setData(authorEntity);
			}
		}catch (Exception e) {
			log.error("error in getAuthorAndBooks");
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		}
		return response;
	}
}
