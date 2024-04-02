package com.project.springboard.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.springboard.dto.PostDTO;
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
class PostControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper om;

	@DisplayName("[POST] /api/post 게시글 작성")
	@Test
	void createPost_Success() throws Exception {
		PostDTO postDto = PostDTO.builder()
			.title("title")
			.content("content")
			.build();

		mockMvc.perform(post("/api/post")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(postDto)))
			.andExpect(status().isCreated())
			.andExpect(content().string("Post created successfully"));
	}

	@DisplayName("[PATCH] /api/post 게시글 수정")
	@Test
	void updatePost_Success() throws Exception {
		PostDTO postDto = PostDTO.builder()
			.id(1L)
			.title("title")
			.content("new content")
			.build();

		mockMvc.perform(patch("/api/post")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(postDto)))
			.andExpect(status().isCreated())
			.andExpect(content().string("Post updated successfully"));
	}

	@DisplayName("[DELETE] /api/post 게시글 삭제")
	@Test
	void deletePost_Success() throws Exception {
		PostDTO postDto = PostDTO.builder()
			.id(1L)
			.build();

		mockMvc.perform(delete("/api/post")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(postDto)))
			.andExpect(status().isOk())
			.andExpect(content().string("Post deleted successfully"));
	}

	@DisplayName("[GET] /api/post/{id} 게시글 상세 조회")
	@Test
	void getPostDetail_Success() throws Exception {
		long postId = 1L;
		String url = "/api/post/" + postId;
		mockMvc.perform(get(url)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.userId").value("1"))
			.andExpect(jsonPath("$.email").value("testuser@example.com"))
			.andExpect(jsonPath("$.postId").value("1"))
			.andExpect(jsonPath("$.title").value("title1"))
			.andExpect(jsonPath("$.content").value("content1"))
			.andDo(print());
	}

	@DisplayName("[GET] /api/post/pages/{page} 게시글 목록 유효조회")
	@Test
	void getPostPages_Success() throws Exception {
		int page = 0;
		String url = "/api/post/pages?page=" + page;
		mockMvc.perform(get(url)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@DisplayName("[GET] /api/post/pages/{page} 게시글 목록 공백조회")
	@Test
	void getPostLastPage_Success() throws Exception {
		int page = 2;
		String url = "/api/post/pages?page=" + page;
		mockMvc.perform(get(url)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@DisplayName("[GET] /post/{postId}/comments/pages 댓글 목록 조회")
	@Test
	void getCommentPageOfPost_Success() throws Exception {
		int page = 0;
		long postId = 1L;
		String url = "/api/post/" + postId + "/comments/pages?page=" + page;
		mockMvc.perform(get(url)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print())
			.andExpect(jsonPath("$.content").isArray())
			.andExpect(jsonPath("$.content[0].id").value(1L))
			.andExpect(jsonPath("$.content[0].userId").value(1L))
			.andExpect(jsonPath("$.size").value(5));
	}
}