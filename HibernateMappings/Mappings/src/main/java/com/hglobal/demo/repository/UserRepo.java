package com.hglobal.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hglobal.demo.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

	@Query(value = "select email from User where email =:emailID", nativeQuery = true)
	List<String> getEmailID(@Param("emailID") String emailID);
}
