package com.epam.usermanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.usermanagement.entities.User;

public interface UserRepository extends JpaRepository<User, String> {
}
