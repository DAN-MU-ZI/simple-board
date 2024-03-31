package com.project.springboard.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.project.springboard.domain.Post.Post;
import com.project.springboard.domain.User.User;
import com.project.springboard.dto.PostDTO;
import com.project.springboard.dto.PostDetailDTO;
import com.project.springboard.dto.PostsByUserDTO;
import com.project.springboard.repository.PostRepository;
import jakarta.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Transactional
class PostServiceTest {
	@InjectMocks
	private PostService service;
	@Mock
	private PostRepository repository;

	@DisplayName("게시글 작성")
	@Test
	void givenPost_whenSave_thenSuccess() {
		User user = User.builder().build();
		PostDTO postDto = PostDTO.builder().title("title").content("content").build();

		service.savePost(user, postDto);

		verify(repository).save(any(Post.class));
	}

	@DisplayName("게시글 수정")
	@Test
	void givenPost_whenUpdate_thenSuccess() {
		Long existPostId = 1L;
		Post existPost = Post.builder()
			.id(existPostId)
			.title("old title")
			.content("old content")
			.build();

		when(repository.findById(existPostId)).thenReturn(Optional.of(existPost));

		String newTitle = "new title";
		String newContent = "new content";
		PostDTO updatedPostDto = PostDTO.builder()
			.id(existPost.getId())
			.title(newTitle)
			.content(newContent)
			.build();

		Post expectedUpdatedPost = Post.builder()
			.id(existPostId)
			.title(newTitle)
			.content(newContent)
			.build();
		when(repository.save(any(Post.class))).thenReturn(expectedUpdatedPost);

		PostDTO updatedPost = service.updatePost(updatedPostDto);

		assertThat(updatedPost.getTitle()).isEqualTo(newTitle);
		assertThat(updatedPost.getContent()).isEqualTo(newContent);

		verify(repository).save(any(Post.class));
	}

	@DisplayName("게시글 삭제")
	@Test
	void givenPost_whenDelete_thenSuccess() {
		Long postId = 1L;
		PostDTO postDto = PostDTO.builder()
			.id(postId)
			.build();
		Post post = Post.builder()
			.id(postId)
			.build();

		when(repository.findById(postId)).thenReturn(Optional.of(post));

		service.deletePost(postDto);

		verify(repository).delete(post);
	}

	@DisplayName("게시글 목록 조회")
	@Test
	void givenNothing_whenFindAllPosts_thenReturnPosts() {
		Post post1 = Post.builder()
			.id(1L)
			.title("Post 1")
			.content("Content 1")
			.build();

		Post post2 = Post.builder()
			.id(2L)
			.title("Post 2")
			.content("Content 2")
			.build();

		when(repository.findAll()).thenReturn(Arrays.asList(post1, post2));

		List<PostDTO> foundPosts = service.findAllPosts();

		assertEquals(2, foundPosts.size());
		assertEquals(post1.getTitle(), foundPosts.get(0).getTitle());
		assertEquals(post2.getTitle(), foundPosts.get(1).getTitle());

		verify(repository, times(1)).findAll();
	}

	@DisplayName("게시글 상세 조회")
	@Test
	void givenPostId_whenReadPost_thenReturnPost() {
		// 작성자 정보, 게시글 제목, 내용
		long postId = 1L;
		User user = User.builder()
			.email("test@gmail.com")
			.build();

		Post post = Post.builder()
			.user(user)
			.title("post title")
			.content("post content")
			.build();
		when(repository.findById(postId)).thenReturn(Optional.ofNullable(post));
		PostDetailDTO postDetail = service.getPostDetail(postId);

		verify(repository).findById(postId);
		assertThat(postDetail.getEmail()).isEqualTo(user.getEmail());
		assertThat(postDetail.getTitle()).isEqualTo(post.getTitle());
		assertThat(postDetail.getContent()).isEqualTo(post.getContent());
	}

	@DisplayName("내가 작성한 게시글 조회")
	@Test
	void givenUser_whenFindPostsOfUser_thenReturnPostsOfUser() {
		User user = User.builder()
			.email("hello@gmail.com")
			.build();

		Post post1 = Post.builder()
			.user(user)
			.title("post1 title")
			.content("post1 content")
			.build();
		Post post2 = Post.builder()
			.user(user)
			.title("post2 title")
			.content("post2 content")
			.build();
		List<Post> posts = List.of(post1, post2);

		when(repository.findAllByUser(user)).thenReturn(posts);
		PostsByUserDTO postsByUser = service.getPostsByUser(user);
		verify(repository).findAllByUser(user);
		assertThat(postsByUser.getPosts().size()).isEqualTo(posts.size());
	}
}
