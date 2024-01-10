package com.epam.bookmanagement.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.bookmanagement.entities.Book;

public interface BookRepository extends JpaRepository<Book,Integer>{
	Optional<Book> findByName(String name);
}
