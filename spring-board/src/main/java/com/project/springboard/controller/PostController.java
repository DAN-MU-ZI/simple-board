package com.project.springboard.controller;

import com.project.springboard.domain.User.User;
import com.project.springboard.dto.PostCommentDTO;
import com.project.springboard.dto.PostDTO;
import com.project.springboard.dto.PostDetailDTO;
import com.project.springboard.dto.PostPageDTO;
import com.project.springboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/post")
@RestController
public class PostController {
	private final PostService postService;

	@PostMapping
	public ResponseEntity<String> createPost(@RequestBody PostDTO postDto) {
		User user = User.builder()
			.id(1L)
			.build();
		postService.savePost(user, postDto);

		return ResponseEntity.status(HttpStatus.CREATED).body("Post created successfully");
	}

	@PatchMapping
	public ResponseEntity<String> updatePost(@RequestBody PostDTO postDto) {
		postService.updatePost(postDto);

		return ResponseEntity.status(HttpStatus.CREATED).body("Post updated successfully");
	}

	@DeleteMapping
	public ResponseEntity<String> deletePost(@RequestBody PostDTO postDto) {
		postService.deletePost(postDto);

		return ResponseEntity.status(HttpStatus.OK).body("Post deleted successfully");
	}

	@GetMapping("/{postId}")
	public ResponseEntity<PostDetailDTO> getPostDetail(@PathVariable(value = "postId") long postId) {
		PostDetailDTO postDetail = postService.getPostDetail(postId);

		return ResponseEntity.status(HttpStatus.OK).body(postDetail);
	}

	@GetMapping("/pages")
	public ResponseEntity<Page<PostPageDTO>> getPostPage(@RequestParam(value = "page", defaultValue = "0") int page) {
		PageRequest pageRequest = PageRequest.of(page, 5);
		Page<PostPageDTO> response = postService.findAllPostsWithPageRequest(pageRequest);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/{postId}/comments/pages")
	public ResponseEntity<Page<PostCommentDTO>> getCommentPage(@PathVariable(value = "postId") final Long postId,
		@RequestParam(value = "page", defaultValue = "0") int page) {
		PageRequest pageRequest = PageRequest.of(page, 5);
		Page<PostCommentDTO> response = postService.findCommentsByPostId(postId, pageRequest);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
