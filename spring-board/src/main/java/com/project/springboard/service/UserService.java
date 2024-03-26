package com.project.springboard.service;

import com.project.springboard.domain.User.User;
import com.project.springboard.dto.UserRequestDto;
import com.project.springboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public User addUser(UserRequestDto userRequestDto) {
		User user = User.builder().email(userRequestDto.getEmail()).build();
		User savedUser;
		try {
			savedUser = userRepository.save(user);
		} catch (RuntimeException e) {
			throw new RuntimeException("이메일이 중복");
		}

		return savedUser;
	}

	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public User findById(Long id) {
		User findUser;

		findUser = userRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("유저 정보를 찾을 수 없음"));

		return findUser;
	}
}
