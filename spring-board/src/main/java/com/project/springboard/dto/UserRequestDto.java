package com.project.springboard.dto;

import com.project.springboard.domain.Post.Post;
import com.project.springboard.domain.User.User;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserRequestDto {
	private final List<Post> posts = new ArrayList<>();
	private final Long id;
	private final String email;

	private final boolean deleted;

	@Builder
	public UserRequestDto(final Long id, final String email, final boolean deleted) {
		this.id = id;
		this.email = email;
		this.deleted = deleted;
	}

	public User toEntity() {
		return User.builder().email(this.email).build();
	}

	public static UserRequestDto from(final User user) {
		return UserRequestDto.builder()
			.id(user.getId())
			.email(user.getEmail())
			.deleted(user.isDeleted())
			.build();
	}
}
