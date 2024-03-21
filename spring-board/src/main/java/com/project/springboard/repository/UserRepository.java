package com.project.springboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.springboard.domain.User.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String mail);
}
