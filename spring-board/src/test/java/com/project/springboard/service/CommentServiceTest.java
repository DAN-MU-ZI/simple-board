package com.project.springboard.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.project.springboard.domain.Comment.Comment;
import com.project.springboard.domain.Post.Post;
import com.project.springboard.domain.User.User;
import com.project.springboard.dto.Comment.CommentCreateDTO;
import com.project.springboard.dto.Comment.CommentDeleteDto;
import com.project.springboard.dto.UserRequestDto;
import com.project.springboard.repository.CommentRepository;
import com.project.springboard.repository.PostRepository;
import com.project.springboard.repository.UserRepository;
import jakarta.transaction.Transactional;
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
public class CommentServiceTest {
	@InjectMocks
	private CommentService service;

	@Mock
	private CommentRepository commentRepository;
	@Mock
	private UserRepository userRepository;
	@Mock
	private PostRepository postRepository;

	@DisplayName("댓글 작성")
	@Test
	void givenComment_whenSave_thenCommentIsSaved() {
		User user = User.builder()
			.id(1L)
			.email("hello@gmail.com")
			.build();
		Post post = Post.builder()
			.id(1L)
			.user(user)
			.title("title")
			.content("content")
			.build();
		Comment comment = Comment.builder()
			.user(user)
			.post(post)
			.content("content")
			.build();
		CommentCreateDTO commentCreateDto = CommentCreateDTO.builder()
			.user(user)
			.post(post)
			.content("content")
			.build();

		when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
		when(commentRepository.save(any(Comment.class))).thenReturn(comment);

		CommentCreateDTO savedCommentCreateDto = service.saveComment(user, commentCreateDto);

		verify(commentRepository).save(any(Comment.class));
		assertThat(savedCommentCreateDto.getUser()).isEqualTo(commentCreateDto.getUser());
		assertThat(savedCommentCreateDto.getPost()).isEqualTo(commentCreateDto.getPost());
		assertThat(savedCommentCreateDto.getContent()).isEqualTo(commentCreateDto.getContent());
	}

	@DisplayName("댓글 수정")
	@Test
	void givenComment_whenUpdateComment_thenCommentIsUpdated() {
		User user = User.builder()
			.id(1L)
			.email("email")
			.build();
		Post post = Post.builder()
			.id(1L)
			.user(user)
			.title("post title")
			.content("post content")
			.build();

		Long existCommentId = 1L;
		Comment existComment = Comment.builder()
			.id(existCommentId)
			.user(user)
			.post(post)
			.content("comment content")
			.build();
		when(commentRepository.findById(existCommentId)).thenReturn(Optional.of(existComment));

		String newContent = "new content";
		Comment expectedUpdatedComment = Comment.builder()
			.id(existCommentId)
			.user(user)
			.post(post)
			.content("comment content")
			.build();

		CommentCreateDTO updatedDto = CommentCreateDTO.builder()
			.id(existComment.getId())
			.user(existComment.getUser())
			.post(existComment.getPost())
			.content(newContent)
			.build();
		when(commentRepository.save(any(Comment.class))).thenReturn(expectedUpdatedComment);
		CommentCreateDTO updatedComment = service.updateComment(updatedDto);

		assertThat(updatedComment.getContent()).isEqualTo(newContent);
		assertThat(updatedComment.getUser()).isEqualTo(user);
		assertThat(updatedComment.getPost()).isEqualTo(post);

		verify(commentRepository).save(any(Comment.class));
	}

	@DisplayName("댓글 삭제")
	@Test
	void givenComment_whenDeleteByCommentEntity_thenCommentIsDeleted() {
		Long existCommentId = 1L;
		User user = User.builder()
			.id(1L)
			.build();
		Comment comment = Comment.builder()
			.id(existCommentId)
			.user(user)
			.content("comment content")
			.build();

		CommentDeleteDto commentDeleteDto = CommentDeleteDto.builder()
			.id(comment.getId())
			.build();
		when(commentRepository.findById(existCommentId)).thenReturn(Optional.of(comment));

		service.deleteComment(user, commentDeleteDto);

		verify(commentRepository).delete(comment);
	}

	@DisplayName("나의 댓글 목록 조회")
	@Test
	void givenUser_whenFindAllCommentsOfUser_thenReturnCommentsOfUser() {
		User user = User.builder()
			.id(1L)
			.email("hello@gmail.com")
			.build();
		UserRequestDto userRequestDto = UserRequestDto.from(user);

		Comment comment1 = Comment.builder()
			.content("content 1")
			.build();
		Comment comment2 = Comment.builder()
			.content("content 2")
			.build();
		List<Comment> comments = List.of(comment1, comment2);

		when(userRepository.findById(userRequestDto.getId())).thenReturn(Optional.of(user));
		when(commentRepository.findAllByUser(user)).thenReturn(comments);
		List<CommentCreateDTO> result = service.findAllByUser(userRequestDto);

		assertThat(result).isNotNull();
		assertThat(comments.size()).isEqualTo(2);
		verify(commentRepository).findAllByUser(user);
	}
}

