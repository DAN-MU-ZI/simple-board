package com.project.springboard.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.project.springboard.domain.User.User;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
public class UserRepositoryTest {

	private final UserRepository userRepository;

	@Autowired
	public UserRepositoryTest(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Test
	@DisplayName("UserRepository가 Null이 아님")
	public void userRepositoryIsNotNull() {
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
		User findUser = userRepository.findById(savedUser.getId())
			.orElse(null);

		//then
		assertThat(findUser).isNotNull();
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
		assertThat(userRepository.findById(savedUser.getId() + 1)).isNotPresent();
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

		// then
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
		String newEmail = "lee@naver.com";

		User user = User.builder()
			.email("kim@naver.com")
			.build();

		//when
		userRepository.save(user);

		User updatedUser = User.builder()
			.id(user.getId())
			.email(newEmail)
			.deleted(user.isDeleted())
			.build();
		userRepository.save(updatedUser);

		User findUser = userRepository.findById(user.getId())
			.orElseThrow(() -> new RuntimeException("User 를 찾을 수 없습니다."));

		//then
		assertThat(findUser.getEmail()).isEqualTo(newEmail);
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
