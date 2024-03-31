package com.project.springboard.service;

import com.project.springboard.domain.Post.Post;
import com.project.springboard.domain.User.User;
import com.project.springboard.dto.PostDTO;
import com.project.springboard.dto.PostDetailDTO;
import com.project.springboard.dto.PostsByUserDTO;
import com.project.springboard.repository.PostRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
public class PostService {

	private final PostRepository postRepository;

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

	public PostDetailDTO getPostDetail(final long postId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new IllegalArgumentException("해당하는 Post가 없습니다. id=" + postId));

		return new PostDetailDTO(post);
	}

	public PostsByUserDTO getPostsByUser(final User user) {
		List<Post> posts = postRepository.findAllByUser(user);
		return new PostsByUserDTO(posts);
	}
}
