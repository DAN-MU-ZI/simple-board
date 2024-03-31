package com.project.springboard.dto;

import com.project.springboard.domain.Post.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostPageDTO {
	private final String email;

	private final Long postId;
	private final String title;

	@Builder
	public PostPageDTO(final String email, final Long postId, final String title) {
		this.email = email;
		this.postId = postId;
		this.title = title;
	}

	public static PostPageDTO from(Post post) {
		return PostPageDTO.builder()
			.email(post.getUser().getEmail())
			.postId(post.getId())
			.title(post.getTitle())
			.build();
	}
}
