package com.project.springboard.dto;

import com.project.springboard.domain.Post.Post;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class PostsByUserDTO {
	private final List<PostDTO> posts = new ArrayList<>();

	public PostsByUserDTO(final List<Post> posts) {
		posts.stream().map(PostDTO::new).forEach(this.posts::add);
	}
}
