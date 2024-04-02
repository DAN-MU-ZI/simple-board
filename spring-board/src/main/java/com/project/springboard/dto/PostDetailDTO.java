package com.project.springboard.dto;

import com.project.springboard.domain.Post.Post;
import lombok.Getter;

@Getter
public class PostDetailDTO {
	private final Long userId;
	private final String email;

	private final Long postId;
	private final String title;
	private final String content;

	public PostDetailDTO(final Post post) {
		this.userId = post.getUser().getId();
		this.email = post.getUser().getEmail();

		this.postId = post.getId();
		this.title = post.getTitle();
		this.content = post.getContent();
	}
}
