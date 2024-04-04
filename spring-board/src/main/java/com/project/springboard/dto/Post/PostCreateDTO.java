package com.project.springboard.dto.Post;

import com.project.springboard.domain.Post.Post;
import lombok.Builder;

public record PostCreateDTO(Long id, String title, String content) {
	@Builder
	public PostCreateDTO {
	}

	public static PostCreateDTO from(Post post) {
		return PostCreateDTO.builder()
			.id(post.getId())
			.title(post.getTitle())
			.content(post.getContent())
			.build();
	}
}
