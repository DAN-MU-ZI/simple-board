package com.project.springboard.dto;

import com.project.springboard.domain.Post.Post;
import com.project.springboard.dto.Post.PostCreateDTO;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class PostsByUserDTO {
	private final List<PostCreateDTO> posts = new ArrayList<>();

	public PostsByUserDTO(final List<Post> posts) {
		posts.stream().map(PostCreateDTO::from).forEach(this.posts::add);
	}
}
