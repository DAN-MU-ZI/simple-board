package com.project.springboard.dto.Comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(force = true)
@Getter
public class CommentUpdateDTO {
	private final Long id;
	private final String content;

	@Builder
	public CommentUpdateDTO(final Long id, final String content) {
		this.id = id;
		this.content = content;
	}
}
