package com.project.springboard.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.project.springboard.domain.User.User;

@DataJpaTest
public class UserRepositoryTest {

	private final UserRepository userRepository;

	@Autowired
	public UserRepositoryTest(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Test
	@DisplayName("UserRepository가 Null이 아님")
	public void UserRepositoryIsNotNull() {
		assertThat(userRepository).isNotNull();
	}

	@Test
	@DisplayName("유저 등록 성공")
	public void addUser() {
		//given
		User user = User.builder().email("kim@naver.com").build();

		//when
		User savedUser = userRepository.save(user);

		//then
		assertThat(savedUser.getId()).isNotNull();
		assertThat(savedUser.getEmail()).isEqualTo("kim@naver.com");
	}

	@Test
	@DisplayName("유저 등록 실패")
	public void addUserX() {
		//given
		User user1 = User.builder().email("kim@naver.com").build();
		User user2 = User.builder().email("kim@naver.com").build();

		//when
		userRepository.save(user1);

		//then
		assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(user2));
	}

	@Test
	@DisplayName("id로 유저 조회")
	public void findById() {
		//given
		User user1 = User.builder().email("kim@naver.com").build();
		User savedUser = userRepository.save(user1);

		//when
		User findUser = userRepository.findById(savedUser.getId()).get();

		//then
		assertThat(findUser.getId()).isNotNull();
		assertThat(findUser.getEmail()).isEqualTo(savedUser.getEmail());
	}

	@Test
	@DisplayName("id로 유저 조회 실패")
	public void findByIdX() {
		//given
		User user1 = User.builder().email("kim@naver.com").build();
		User savedUser = userRepository.save(user1);

		//when, then
		assertThrows(NoSuchElementException.class,
			() -> userRepository.findById(savedUser.getId() + 1).get());
	}

	@Test
	@DisplayName("이메일로 유저 조회")
	public void findByEmail() {
		//given
		User user1 = User.builder().email("kim@naver.com").build();
		User user2 = User.builder().email("lee@naver.com").build();

		//when
		userRepository.save(user1);
		userRepository.save(user2);
		User userA = userRepository.findByEmail("kim@naver.com");
		User userB = userRepository.findByEmail("lee@naver.com");
		User userC = userRepository.findByEmail("park@naver.com");

		//then
		assertThat(userA.getEmail()).isEqualTo("kim@naver.com");
		assertThat(userB.getEmail()).isEqualTo("lee@naver.com");
		assertThat(userC).isNull();
	}

	@Test
	@DisplayName("유저 전체 조회")
	public void findAll() {
		//given
		User user1 = User.builder().email("kim@naver.com").build();
		User user2 = User.builder().email("lee@naver.com").build();

		//when
		userRepository.save(user1);
		userRepository.save(user2);
		List<User> users = userRepository.findAll();

		//then
		assertThat(users.size()).isEqualTo(2);
		assertThat(users.get(0).getEmail()).isEqualTo("kim@naver.com");
		assertThat(users.get(1).getEmail()).isEqualTo("lee@naver.com");
	}

	@Test
	@DisplayName("유저 정보 수정")
	public void userUpdate() {
		//given
		User user = User.builder().email("kim@naver.com").build();

		//when
		userRepository.save(user);
		userRepository.findById(user.getId()).get().setEmail("lee@naver.com");
		User findUser = userRepository.findById(user.getId()).get();

		//then
		assertThat(findUser.getEmail()).isEqualTo("lee@naver.com");
	}

	@Test
	@DisplayName("유저 삭제")
	public void deleteUser() {
		//given
		User user = User.builder().email("kim@naver.com").build();

		//when
		userRepository.save(user);
		userRepository.deleteById(user.getId());

		//then
		assertThat(userRepository.findAll().size()).isEqualTo(0);
	}
}
