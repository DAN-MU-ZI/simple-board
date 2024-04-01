package com.project.springboard.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(force = true)
@Getter
public class CommentDeleteDto {
	private final Long id;

	@Builder
	public CommentDeleteDto(final Long id) {
		this.id = id;
	}
}
