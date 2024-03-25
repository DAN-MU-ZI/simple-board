package com.project.springboard.dto;

import com.project.springboard.domain.Post.Post;
import com.project.springboard.domain.User.User;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
public class UserRequestDto {
	private Long id;

	private final List<Post> posts = new ArrayList<>();

	private String email;

	private boolean deleted;


	public User toEntity() {
		return User.builder().email(this.email).build();
	}

	@Builder
	public UserRequestDto(final Long id, final String email, final boolean deleted) {
		this.id = id;
		this.email = email;
		this.deleted = deleted;
	}
}
