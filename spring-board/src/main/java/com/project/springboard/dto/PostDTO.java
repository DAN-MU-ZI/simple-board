package com.project.springboard.dto;

import com.project.springboard.domain.Post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PostDTO {
	private Long id;
	private String title;
	private String content;

	@Builder
	public PostDTO(final Long id, final String title, final String content) {
		this.id = id;
		this.title = title;
		this.content = content;
	}

	public PostDTO(final Post post) {
		this.id = post.getId();
		this.title = post.getTitle();
		this.content = post.getContent();
	}

	public static PostDTO from(Post post) {
		return PostDTO.builder()
			.id(post.getId())
			.title(post.getTitle())
			.content(post.getContent())
			.build();
	}
}
