package com.project.springboard.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import com.project.springboard.domain.User.User;
import com.project.springboard.dto.UserRequestDto;
import com.project.springboard.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@InjectMocks
	private UserService userService;
	@Mock
	private UserRepository userRepository;

	@Test
	@DisplayName("유저 등록 성공")
	public void addUser() {
		//given
		UserRequestDto userRequest = new UserRequestDto("kim@naver.com");
		User user = User.builder().id(1L).email("kim@naver.com").build();

		//stub
		when(userRepository.save(any(User.class))).thenReturn(user);

		//when
		User savedUser = userService.addUser(userRequest);

		//then
		assertThat(savedUser.getId()).isNotNull();
		assertThat(savedUser.getId()).isEqualTo(user.getId());
		assertThat(savedUser.getEmail()).isEqualTo(user.getEmail());
	}

	@Test
	@DisplayName("유저 등록 실패")
	public void addUserX() {
		//given
		UserRequestDto userRequest = new UserRequestDto("kim@naver.com");
		User user = userRequest.toEntity();

		//stub
		when(userRepository.save(user)).thenThrow(DataIntegrityViolationException.class);

		//when

		//then
		assertThatThrownBy(() -> userService.addUser(userRequest)).isInstanceOf(RuntimeException.class);
	}

	@Test
	@DisplayName("유저 삭제")
	public void deleteUser() {
		//given
		Long userId = 1L;

		//stub

		//when
		userService.deleteUser(userId);

		//then
		verify(userRepository).deleteById(userId);
	}

	@Test
	@DisplayName("유저 정보 조회")
	public void userInfo() {
		//given
		Long userId = 1L;
		User savedUser = User.builder().id(1L).email("kim@naver.com").build();

		//stub
		when(userRepository.findById(userId)).thenReturn(Optional.of(savedUser));

		//when
		User findUser = userService.findById(userId);

		//then
		assertThat(findUser.getId()).isEqualTo(savedUser.getId());
		assertThat(findUser.getEmail()).isEqualTo(savedUser.getEmail());
	}

	@Test
	@DisplayName("유저 정보 조회 실패")
	public void userInfoX() {
		//given
		Long userId = 2L;
		User savedUser = User.builder().id(1L).email("kim@naver.com").build();

		//stub
		when(userRepository.findById(userId)).thenThrow(RuntimeException.class);

		//when

		//then
		assertThatThrownBy(() -> userService.findById(userId));
	}
}
