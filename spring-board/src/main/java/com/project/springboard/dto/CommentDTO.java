package com.project.springboard.dto;

import com.project.springboard.domain.Comment.Comment;
import com.project.springboard.domain.Post.Post;
import com.project.springboard.domain.User.User;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentDTO {
	private Long id;
	private User user;
	private Post post;
	private String content;

	@Builder
	public CommentDTO(final Long id, final User user, final Post post, final String content) {
		this.id = id;
		this.user = user;
		this.post = post;
		this.content = content;
	}

	public static CommentDTO from(final Comment comment) {
		return CommentDTO.builder()
			.id(comment.getId())
			.user(comment.getUser())
			.post(comment.getPost())
			.content(comment.getContent())
			.build();
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		CommentDTO that = (CommentDTO)o;
		return Objects.equals(id, that.id) && Objects.equals(user, that.user)
			&& Objects.equals(post, that.post) && Objects.equals(content, that.content);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, user, post, content);
	}
}
