package com.project.springboard.dto.Post;

import com.project.springboard.domain.Post.Post;
import lombok.Builder;

public record PostPageDTO(String email, Long postId, String title) {
	@Builder
	public PostPageDTO {
	}

	public static PostPageDTO from(Post post) {
		return PostPageDTO.builder()
			.email(post.getUser().getEmail())
			.postId(post.getId())
			.title(post.getTitle())
			.build();
	}
}
