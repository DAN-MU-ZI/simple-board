package com.project.springboard.dto;

import com.project.springboard.domain.Post.Post;
import lombok.Getter;

@Getter
public class PostDetailDTO {
	private String email;
	private String title;
	private String content;

	public PostDetailDTO(final Post post) {
		this.email = post.getUser().getEmail();
		this.title = post.getTitle();
		this.content = post.getContent();
	}
}
