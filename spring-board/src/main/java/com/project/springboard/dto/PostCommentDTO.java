package com.project.springboard.dto;

import com.project.springboard.domain.Comment.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostCommentDTO {
	private final Long id;
	private final Long userId;
	private final String content;

	@Builder
	public PostCommentDTO(final Long id, final Long userId, final String content) {
		this.id = id;
		this.userId = userId;
		this.content = content;
	}

	public static PostCommentDTO from(final Comment comment) {
		return PostCommentDTO.builder()
			.id(comment.getId())
			.userId(comment.getUser().getId())
			.content(comment.getContent())
			.build();
	}
}
