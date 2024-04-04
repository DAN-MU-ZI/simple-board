package com.project.springboard.dto.Post;

import com.project.springboard.domain.Post.Post;
import lombok.Builder;

public record PostDetailDTO(Long userId, String email, Long postId, String title, String content) {
	@Builder
	public PostDetailDTO {
	}

	public static PostDetailDTO from(final Post post) {
		return PostDetailDTO.builder()
			.userId(post.getUser().getId())
			.email(post.getUser().getEmail())
			.postId(post.getId())
			.title(post.getTitle())
			.content(post.getContent())
			.build();

	}
}
