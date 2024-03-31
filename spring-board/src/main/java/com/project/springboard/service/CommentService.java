package com.project.springboard.service;

import com.project.springboard.domain.Comment.Comment;
import com.project.springboard.domain.Post.Post;
import com.project.springboard.domain.User.User;
import com.project.springboard.dto.CommentDTO;
import com.project.springboard.dto.UserRequestDto;
import com.project.springboard.repository.CommentRepository;
import com.project.springboard.repository.PostRepository;
import com.project.springboard.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final UserRepository userRepository;
	private final PostRepository postRepository;
	private final CommentRepository commentRepository;

	private Comment getExistingComment(final CommentDTO commentDto) {
		return commentRepository.findById(commentDto.getId())
			.orElseThrow(() -> new IllegalArgumentException("해당하는 Comment가 없습니다. id=" + commentDto.getId()));
	}

	public CommentDTO saveComment(final CommentDTO commentDto) {
		if (commentDto.getUser() == null) {
			throw new IllegalArgumentException("User cannot be null");
		}
		if (commentDto.getPost() == null) {
			throw new IllegalArgumentException("Post cannot be null");
		}

		User user = userRepository.findById(commentDto.getUser().getId())
			.orElseThrow(() -> new IllegalArgumentException("User cannot be null"));
		Post post = postRepository.findById(commentDto.getPost().getId())
			.orElseThrow(() -> new IllegalArgumentException("Post cannot be null"));

		Comment comment = Comment.builder()
			.user(user)
			.post(post)
			.content(commentDto.getContent())
			.build();

		commentRepository.save(comment);

		return CommentDTO.from(comment);
	}

	public CommentDTO updateComment(final CommentDTO updatedDto) {
		Comment comment = getExistingComment(updatedDto);

		Comment updatedComment = Comment.builder()
			.id(comment.getId())
			.user(comment.getUser())
			.post(comment.getPost())
			.content(updatedDto.getContent())
			.build();
		commentRepository.save(updatedComment);

		return CommentDTO.from(updatedComment);
	}

	public void deleteComment(final CommentDTO commentDto) {
		Comment comment = getExistingComment(commentDto);

		commentRepository.delete(comment);
	}

	public List<CommentDTO> findAllByUser(final UserRequestDto userRequestDto) {
		User user = userRepository.findById(userRequestDto.getId())
			.orElseThrow(() -> new IllegalArgumentException("User cannot find"));

		List<Comment> comments = commentRepository.findAllByUser(user);

		return comments.stream()
			.map(CommentDTO::from)
			.collect(Collectors.toList());
	}
}
