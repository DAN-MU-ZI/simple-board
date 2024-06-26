package com.project.springboard.service;

import com.project.springboard.domain.Comment.Comment;
import com.project.springboard.domain.Post.Post;
import com.project.springboard.domain.User.User;
import com.project.springboard.dto.CommentCreateDTO;
import com.project.springboard.dto.CommentDeleteDto;
import com.project.springboard.dto.CommentUpdateDTO;
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
		return commentRepository.findById(commentCreateDto.getId())
			.orElseThrow(() -> new IllegalArgumentException("해당하는 Comment가 없습니다. id=" + commentCreateDto.getId()));
	}

	public CommentCreateDTO saveComment(final User user, final CommentCreateDTO commentCreateDto) {
		if (commentCreateDto.getPost() == null) {
			throw new IllegalArgumentException("Post cannot be null");
		}

		Post post = postRepository.findById(commentCreateDto.getPost().getId())
			.orElseThrow(() -> new IllegalArgumentException("Post cannot be null"));

		Comment comment = Comment.builder()
			.user(user)
			.post(post)
			.content(commentCreateDto.getContent())
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
			.content(updatedDto.getContent())
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
