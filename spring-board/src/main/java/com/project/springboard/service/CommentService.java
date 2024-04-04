package com.project.springboard.service;

import com.project.springboard.domain.Comment.Comment;
import com.project.springboard.domain.Post.Post;
import com.project.springboard.domain.User.User;
import com.project.springboard.dto.Comment.CommentCreateDTO;
import com.project.springboard.dto.Comment.CommentDeleteDto;
import com.project.springboard.dto.Comment.CommentUpdateDTO;
import com.project.springboard.dto.UserRequestDto;
import com.project.springboard.repository.CommentRepository;
import com.project.springboard.repository.PostRepository;
import com.project.springboard.repository.UserRepository;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {

	private final UserRepository userRepository;
	private final PostRepository postRepository;
	private final CommentRepository commentRepository;

	private Comment getExistingComment(final CommentCreateDTO commentCreateDto) {
		return commentRepository.findById(commentCreateDto.id())
			.orElseThrow(() -> new IllegalArgumentException("해당하는 Comment가 없습니다. id=" + commentCreateDto.id()));
	}

	public CommentCreateDTO createComment(final User user, final CommentCreateDTO commentCreateDto) {
		Post post = postRepository.findById(commentCreateDto.postId())
			.orElseThrow(() -> new IllegalArgumentException("Post cannot be null"));

		Comment comment = Comment.builder()
			.user(user)
			.post(post)
			.content(commentCreateDto.content())
			.build();

		commentRepository.save(comment);

		return CommentCreateDTO.from(comment);
	}

	public CommentCreateDTO updateComment(final CommentCreateDTO updatedDto) {
		Comment comment = getExistingComment(updatedDto);

		Comment updatedComment = Comment.builder()
			.id(comment.getId())
			.user(comment.getUser())
			.post(comment.getPost())
			.content(updatedDto.content())
			.build();
		commentRepository.save(updatedComment);

		return CommentCreateDTO.from(updatedComment);
	}

	public List<CommentCreateDTO> findAllByUser(final UserRequestDto userRequestDto) {
		User user = userRepository.findById(userRequestDto.getId())
			.orElseThrow(() -> new IllegalArgumentException("User cannot find"));

		List<Comment> comments = commentRepository.findAllByUser(user);

		return comments.stream()
			.map(CommentCreateDTO::from)
			.collect(Collectors.toList());
	}

	public CommentCreateDTO updateComment(final User user, final CommentUpdateDTO commentUpdateDto) {
		Comment comment = commentRepository.findById(commentUpdateDto.getId())
			.orElseThrow(() -> new IllegalArgumentException("Comment cannot find"));

		if (!Objects.equals(user.getId(), comment.getUser().getId())) {
			throw new IllegalArgumentException("User is not coincided");
		}

		Comment updatedComment = Comment.builder()
			.id(comment.getId())
			.user(comment.getUser())
			.post(comment.getPost())
			.content(commentUpdateDto.getContent())
			.build();
		commentRepository.save(updatedComment);
		return CommentCreateDTO.from(updatedComment);
	}

	public void deleteComment(final User user, final CommentDeleteDto commentDeleteDto) {
		Comment comment = commentRepository.findById(commentDeleteDto.getId())
			.orElseThrow(() -> new IllegalArgumentException("Comment cannot find"));

		if (!Objects.equals(user.getId(), comment.getUser().getId())) {
			throw new IllegalArgumentException("User is not coincided");
		}
		commentRepository.delete(comment);
	}
}
