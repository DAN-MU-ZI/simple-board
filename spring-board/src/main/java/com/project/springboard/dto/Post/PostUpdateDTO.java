package com.project.springboard.dto.Post;

import com.project.springboard.domain.Post.Post;
import lombok.Builder;

public record PostUpdateDTO(Long id, String title, String content) {
	@Builder
	public PostUpdateDTO {
	}

	public static PostUpdateDTO from(Post post) {
		return PostUpdateDTO.builder()
			.id(post.getId())
			.title(post.getTitle())
			.content(post.getContent())
			.build();
	}
}
