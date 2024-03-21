package com.project.springboard.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.springboard.domain.User.User;
import com.project.springboard.dto.UserRequestDto;
import com.project.springboard.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	@Transactional
	public User addUser(UserRequestDto userRequestDto) {
		User user = User.builder().email(userRequestDto.getEmail()).build();
		User savedUser;
		try {
			savedUser = userRepository.save(user);
		} catch (DataIntegrityViolationException e) {
			throw new RuntimeException("이메일이 중복");
		}

		return savedUser;
	}

	@Transactional
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	@Transactional
	public User findById(Long id) {
		User findUser;
		try {
			findUser = userRepository.findById(id).get();
		} catch (Exception e) {
			throw new RuntimeException("유저 정보를 찾을 수 없음");
		}
		return findUser;
	}
}
