package com.project.springboard.dto.Comment;

import com.project.springboard.domain.Comment.Comment;
import lombok.Builder;

public record CommentCreateDTO(Long id, Long userId, Long postId, String email, String postTitle, String postContent,
							   String content) {
	@Builder
	public CommentCreateDTO {
	}

	public static CommentCreateDTO from(final Comment comment) {
		return CommentCreateDTO.builder()
			.id(comment.getId())
			.userId(comment.getUser().getId())
			.postId(comment.getPost().getId())
			.email(comment.getUser().getEmail())
			.postTitle(comment.getPost().getTitle())
			.postContent(comment.getPost().getContent())
			.content(comment.getContent())
			.build();
	}
}
