package com.project.springboard.controller;

import com.project.springboard.domain.User.User;
import com.project.springboard.dto.Comment.CommentCreateDTO;
import com.project.springboard.dto.Comment.CommentDeleteDto;
import com.project.springboard.dto.Comment.CommentUpdateDTO;
import com.project.springboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/comment")
@RestController
public class CommentController {
	private final CommentService commentService;

	@PostMapping
	public ResponseEntity<String> createComment(@RequestBody CommentCreateDTO commentCreateDto) {
		User user = User.builder()
			.id(1L)
			.build();
		commentService.saveComment(user, commentCreateDto);
		return ResponseEntity.status(HttpStatus.CREATED).body("Comment created successfully");
	}

	@PatchMapping
	public ResponseEntity<String> updateComment(@RequestBody CommentUpdateDTO commentUpdateDto) {
		User user = User.builder()
			.id(1L)
			.build();
		commentService.updateComment(user, commentUpdateDto);
		return ResponseEntity.status(HttpStatus.CREATED).body("Comment updated successfully");
	}

	@DeleteMapping
	public ResponseEntity<String> deleteComment(@RequestBody CommentDeleteDto commentDeleteDto) {
		User user = User.builder()
			.id(1L)
			.build();
		commentService.deleteComment(user, commentDeleteDto);
		return ResponseEntity.status(HttpStatus.OK).body("Comment deleted successfully");
	}
}
