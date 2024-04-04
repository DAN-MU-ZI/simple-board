package com.project.springboard.dto.Post;

import com.project.springboard.domain.Comment.Comment;
import lombok.Builder;

public record PostCommentDTO(Long id, Long userId, String content) {
	@Builder
	public PostCommentDTO {
	}

	public static PostCommentDTO from(final Comment comment) {
		return PostCommentDTO.builder()
			.id(comment.getId())
			.userId(comment.getUser().getId())
			.content(comment.getContent())
			.build();
	}
}
