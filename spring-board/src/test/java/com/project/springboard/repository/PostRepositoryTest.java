package com.project.springboard.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.springboard.domain.Post.Post;
import com.project.springboard.domain.User.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Transactional
class PostRepositoryTest {
	@Autowired
	PostRepository postRepository;
	@Autowired
	UserRepository userRepository;

	@BeforeEach
	void init() {
		User user = User.builder().email("hello@gmail.com").build();
		userRepository.save(user);
	}

	@Test
	void givenPost_whenSave_thenSuccess() {
		User user = userRepository.findById(1L).orElse(null);
		int previousSize = postRepository.findAll().size();
		Post post = Post.builder().content("content").user(user).build();
		postRepository.save(post);

		assertThat(postRepository.findAll().size()).isEqualTo(previousSize + 1);
	}

	@Test
	void givenPostId_whenRead_thenSuccess() {
		Post post = Post.builder().content("content").build();
		postRepository.save(post);

		Post findedPost = postRepository.findById(post.getId()).orElse(null);

		assertThat(findedPost).isNotNull();
	}

	@Test
	void givenPost_whenUpdate_thenSuccess() {
		String previousContent = "content";
		String newContent = "content";
		Post post = Post.builder().content(previousContent).build();
		postRepository.save(post);

		post = Post.builder()
			.id(post.getId())
			.title(post.getTitle())
			.content(newContent)
			.build();
		Post savedPost = postRepository.save(post);

		assertThat(savedPost.getContent()).isEqualTo(newContent);
	}

	@Test
	void givenPostId_whenDelete_thenSuccess() {
		Post post = Post.builder().content("content").build();
		postRepository.save(post);
		int previousSize = postRepository.findAll().size();

		postRepository.deleteById(post.getId());

		assertThat(postRepository.findAll().size()).isEqualTo(previousSize - 1);
	}

	@Sql("/PostControllerData.sql")
	@Test
	void givenPageRequest_whenFindAllLastPage_thenSuccess() {
		int page = 4;
		int pageSize = 3;
		int repoSize = postRepository.findAll().size();
		Pageable pageable = PageRequest.of(page, pageSize);
		Page<Post> posts = postRepository.findAll(pageable);

		assertThat(repoSize).isEqualTo(10);
		assertThat(posts.getNumberOfElements()).isEqualTo(0);
		assertThat(posts.getTotalElements()).isEqualTo(repoSize);
		assertThat(posts.getTotalPages()).isEqualTo(repoSize / pageSize + 1);
	}

	@Sql("/PostControllerData.sql")
	@Test
	void givenPageRequest_whenFindAllMiddlePage_thenSuccess() {
		int page = 3;
		int pageSize = 3;
		int repoSize = postRepository.findAll().size();
		Pageable pageable = PageRequest.of(page, pageSize);
		Page<Post> posts = postRepository.findAll(pageable);

		assertThat(repoSize).isEqualTo(10);
		assertThat(posts.getNumberOfElements()).isEqualTo(repoSize % pageSize);
	}

	@Sql("/PostControllerData.sql")
	@Test
	void givenPageRequest_whenFindAllFirstPage_thenSuccess() {
		int page = 0;
		int pageSize = 3;
		int repoSize = postRepository.findAll().size();
		Pageable pageable = PageRequest.of(page, pageSize);
		Page<Post> posts = postRepository.findAll(pageable);

		assertThat(repoSize).isEqualTo(10);
		assertThat(posts.getNumberOfElements()).isEqualTo(pageSize);
	}
}