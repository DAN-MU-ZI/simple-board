package com.project.springboard.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.springboard.domain.Comment.Comment;
import com.project.springboard.domain.Post.Post;
import com.project.springboard.domain.User.User;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@Transactional
@DataJpaTest
public class CommentRepositoryTest {
	@Autowired
	UserRepository userRepository;
	@Autowired
	PostRepository postRepository;
	@Autowired
	CommentRepository commentRepository;

	private User user;
	private Post post;

	@BeforeEach
	void init() {
		User user = User.builder().email("hello@gmail.com").build();
		this.user = userRepository.save(user);

		Post post = Post.builder()
			.title("title")
			.content("content")
			.user(user)
			.build();
		this.post = postRepository.save(post);
	}

	@Test
	void givenNewComment_whenSave_thenCommentIsCreated() {
		User user = this.user;
		Post post = this.post;

		String content = "content";
		Comment comment = Comment.builder()
			.content(content)
			.user(user)
			.post(post)
			.build();

		Comment savedComment = commentRepository.save(comment);

		assertThat(savedComment.getId()).isNotNull();
		assertThat(savedComment.getContent()).isEqualTo(content);
	}

	@Test
	void givenCommentId_whenFindById_thenCommentIsFound() {
		User user = this.user;
		Post post = this.post;

		String content = "content";
		Comment comment = Comment.builder()
			.content(content)
			.user(user)
			.post(post)
			.build();

		Comment savedComment = commentRepository.save(comment);

		Optional<Comment> foundComment = commentRepository.findById(savedComment.getId());

		assertThat(foundComment).isPresent();
		assertThat(foundComment.get().getContent()).isEqualTo(content);
	}

	@Test
	void givenUpdatedComment_whenSave_thenCommentIsUpdated() {
		User user = this.user;
		Post post = this.post;

		String content = "content";
		Comment comment = Comment.builder()
			.content(content)
			.user(user)
			.post(post)
			.build();
		Comment savedComment = commentRepository.save(comment);

		String newContent = "new content";
		Comment newComment = Comment.builder()
			.id(savedComment.getId())
			.post(savedComment.getPost())
			.user(savedComment.getUser())
			.content(newContent)
			.build();
		Comment updatedComment = commentRepository.save(newComment);

		assertThat(updatedComment.getId()).isEqualTo(savedComment.getId());
		assertThat(updatedComment.getContent()).isEqualTo(newContent);
	}

	@Test
	void givenCommentId_whenDelete_thenCommentIsDeleted() {
		User user = this.user;
		Post post = this.post;

		String content = "content";
		Comment comment = Comment.builder()
			.content(content)
			.user(user)
			.post(post)
			.build();
		Comment savedComment = commentRepository.save(comment);

		commentRepository.delete(savedComment);
		Optional<Comment> deletedComment = commentRepository.findById(savedComment.getId());

		assertThat(deletedComment).isNotPresent();
	}
}
