package com.project.springboard.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.springboard.domain.Post.Post;
import com.project.springboard.domain.User.User;
import com.project.springboard.dto.CommentCreateDTO;
import com.project.springboard.dto.CommentDeleteDto;
import com.project.springboard.dto.CommentUpdateDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@Sql("/PostControllerData.sql")
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper om;

	@DisplayName("[POST] /api/comment 댓글 작성")
	@Test
	void createComment_Success() throws Exception {
		User user = User.builder().id(1L).build();
		Post post = Post.builder().id(1L).build();
		CommentCreateDTO commentCreateDto = CommentCreateDTO.builder()
			.user(user)
			.post(post)
			.content("content11")
			.build();

		mockMvc.perform(post("/api/comment")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(commentCreateDto)))
			.andExpect(status().isCreated())
			.andExpect(content().string("Comment created successfully"));
	}

	@DisplayName("[PATCH] /api/comment 댓글 수정")
	@Test
	void updateComment_Success() throws Exception {
		CommentUpdateDTO commentUpdateDto = CommentUpdateDTO.builder()
			.id(1L)
			.content("new content")
			.build();
		mockMvc.perform(patch("/api/comment")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(commentUpdateDto)))
			.andExpect(status().isCreated())
			.andExpect(content().string("Comment updated successfully"));
	}

	@DisplayName("[DELETE] /api/comment 댓글 삭제")
	@Test
	void deleteComment_Success() throws Exception {
		CommentDeleteDto commentDeleteDto = CommentDeleteDto.builder()
			.id(1L)
			.build();
		mockMvc.perform(delete("/api/comment")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(commentDeleteDto)))
			.andExpect(status().isOk())
			.andExpect(content().string("Comment deleted successfully"));
	}
}
