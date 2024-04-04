package com.project.springboard.dto.Post;

import com.project.springboard.domain.Post.Post;
import lombok.Builder;

public record PostPatchDTO(Long id, String title, String content) {
	@Builder
	public PostPatchDTO {
	}

	public static PostPatchDTO from(Post post) {
		return PostPatchDTO.builder()
			.id(post.getId())
			.title(post.getTitle())
			.content(post.getContent())
			.build();
	}
}
