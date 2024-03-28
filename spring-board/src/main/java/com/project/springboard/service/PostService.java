package com.project.springboard.service;

import com.project.springboard.domain.Post.Post;
import com.project.springboard.domain.User.User;
import com.project.springboard.dto.PostDTO;
import com.project.springboard.repository.PostRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class PostService {
	@Autowired
	private PostRepository postRepository;

	public PostDTO savePost(final User user, final PostDTO postDto) {
		Post post = Post.builder()
			.user(user)
			.title(postDto.getTitle())
			.content(postDto.getContent())
			.build();

		postRepository.save(post);

		return PostDTO.from(post);
	}

	public PostDTO updatePost(final PostDTO postDto) {
		Post existingPost = getExistingPost(postDto);

		Post updatedPost = Post.builder()
			.id(existingPost.getId())
			.user(existingPost.getUser())
			.title(postDto.getTitle())
			.content(postDto.getContent())
			.build();

		postRepository.save(updatedPost);

		return PostDTO.from(updatedPost);
	}

	public void deletePost(final PostDTO postDto) {
		Post existingPost = getExistingPost(postDto);

		postRepository.delete(existingPost);
	}

	private Post getExistingPost(final PostDTO postDto) {
		return postRepository.findById(postDto.getId())
			.orElseThrow(() -> new IllegalArgumentException("해당하는 Post가 없습니다. id=" + postDto.getId()));
	}

	public List<PostDTO> findAllPosts() {

		List<Post> posts = postRepository.findAll();

		return posts.stream()
			.map(PostDTO::from)
			.collect(Collectors.toList());
	}
}
