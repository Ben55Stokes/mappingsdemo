package com.hglobal.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hglobal.demo.entity.AuthorEntity;


public interface AuthorRepository extends JpaRepository<AuthorEntity, Integer>{

}
