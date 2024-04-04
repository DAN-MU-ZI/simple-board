package com.project.springboard.dto.Post;

import com.project.springboard.domain.Post.Post;
import lombok.Builder;

public record PostDeleteDTO(Long id, String title, String content) {

	@Builder
	public PostDeleteDTO {
	}

	public static PostDeleteDTO from(Post post) {
		return PostDeleteDTO.builder()
			.id(post.getId())
			.title(post.getTitle())
			.content(post.getContent())
			.build();
	}
}
