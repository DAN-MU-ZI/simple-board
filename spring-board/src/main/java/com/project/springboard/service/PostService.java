package com.project.springboard.service;

import com.project.springboard.domain.Post.Post;
import com.project.springboard.domain.User.User;
import com.project.springboard.dto.Post.PostCommentDTO;
import com.project.springboard.dto.Post.PostCreateDTO;
import com.project.springboard.dto.Post.PostDeleteDTO;
import com.project.springboard.dto.Post.PostDetailDTO;
import com.project.springboard.dto.Post.PostPageDTO;
import com.project.springboard.dto.Post.PostUpdateDTO;
import com.project.springboard.dto.PostsByUserDTO;
import com.project.springboard.repository.CommentRepository;
import com.project.springboard.repository.PostRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
public class PostService {

	private final PostRepository postRepository;
	private final CommentRepository commentRepository;

	public PostCreateDTO savePost(final User user, final PostCreateDTO postDto) {
		Post post = Post.builder()
			.user(user)
			.title(postDto.title())
			.content(postDto.content())
			.build();

		postRepository.save(post);

		return PostCreateDTO.from(post);
	}

	public PostUpdateDTO updatePost(final PostUpdateDTO postDto) {
		Post existingPost = getExistingPost(postDto.id());

		Post updatedPost = Post.builder()
			.id(existingPost.getId())
			.user(existingPost.getUser())
			.title(postDto.title())
			.content(postDto.content())
			.build();

		postRepository.save(updatedPost);

		return PostUpdateDTO.from(updatedPost);
	}

	public void deletePost(final PostDeleteDTO postDto) {
		Post existingPost = getExistingPost(postDto.id());

		postRepository.delete(existingPost);
	}

	private Post getExistingPost(final Long postId) {
		return postRepository.findById(postId)
			.orElseThrow(() -> new IllegalArgumentException("해당하는 Post가 없습니다. id=" + postId));
	}

	public List<PostCreateDTO> findAllPosts() {

		List<Post> posts = postRepository.findAll();

		return posts.stream()
			.map(PostCreateDTO::from)
			.collect(Collectors.toList());
	}

	public PostDetailDTO getPostDetail(final long postId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new IllegalArgumentException("해당하는 Post가 없습니다. id=" + postId));

		return PostDetailDTO.from(post);
	}

	public PostsByUserDTO getPostsByUser(final User user) {
		List<Post> posts = postRepository.findAllByUser(user);
		return new PostsByUserDTO(posts);
	}

	public Page<PostPageDTO> findAllPostsWithPageRequest(final PageRequest pageRequest) {
		return postRepository.findAll(pageRequest).map(PostPageDTO::from);
	}

	public Page<PostCommentDTO> findCommentsByPostId(final Long postId, final PageRequest pageRequest) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new IllegalArgumentException("해당하는 Post가 없습니다. id=" + postId));

		return commentRepository.findAllByPost(post, pageRequest)
			.map(PostCommentDTO::from);
	}
}
