package com.project.springboard.dto;

import com.project.springboard.domain.User.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

	private String email;

	public User toEntity() {
		return User.builder().email(this.email).build();
	}
}
