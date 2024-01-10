package com.epam.librarymanagement.repositories;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.librarymanagement.entities.Library;

@Transactional
public interface LibraryRepository extends JpaRepository<Library, Integer>{
	Optional<Library> findByUsernameAndBookId(String username,int bookId);
	void deleteByBookId(int bookId);
	void deleteByUsername(String username);
	List<Library> findByUsername(String username);
}
