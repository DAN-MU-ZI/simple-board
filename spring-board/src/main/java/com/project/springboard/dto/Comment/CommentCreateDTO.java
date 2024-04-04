package com.project.springboard.dto.Comment;

import com.project.springboard.domain.Comment.Comment;
import com.project.springboard.domain.Post.Post;
import com.project.springboard.domain.User.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(force = true)
@Getter
public class CommentCreateDTO {
	private final Long id;
	private final User user;
	private final Post post;
	private final String content;

	@Builder
	public CommentCreateDTO(final Long id, final User user, final Post post, final String content) {
		this.id = id;
		this.user = user;
		this.post = post;
		this.content = content;
	}

	public static CommentCreateDTO from(final Comment comment) {
		return CommentCreateDTO.builder()
			.id(comment.getId())
			.user(comment.getUser())
			.post(comment.getPost())
			.content(comment.getContent())
			.build();
	}
}
