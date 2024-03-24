package com.project.springboard.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.project.springboard.domain.Post.Post;
import com.project.springboard.domain.Post.PostDTO;
import com.project.springboard.domain.Post.PostDTO.PostDTOBuilder;
import com.project.springboard.domain.User.User;
import com.project.springboard.repository.PostRepository;
import jakarta.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Transactional
class PostServiceTest {
    @InjectMocks
    private PostService service;
    @Mock
    private PostRepository repository;

    @Test
    void givenPost_whenSave_thenSuccess() {
        User user = User.builder().build();
        PostDTO postDTO = PostDTO.builder().title("title").content("content").build();

        service.savePost(user, postDTO);

        verify(repository).save(any(Post.class));
    }

    @Test
    void givenPost_whenUpdate_thenSuccess(){
        Long existPostId = 1L;
        Post existPost = Post.builder()
                .id(existPostId)
                .title("old title")
                .content("old content")
                .build();

        when(repository.findById(existPostId)).thenReturn(Optional.of(existPost));

        String newTitle = "new title";
        String newContent = "new content";
        PostDTO updatedPostDTO = PostDTO.builder()
                .id(existPost.getId())
                .title(newTitle)
                .content(newContent)
                .build();

        Post expectedUpdatedPost = Post.builder()
                .id(existPostId)
                .title(newTitle)
                .content(newContent)
                .build();
        when(repository.save(any(Post.class))).thenReturn(expectedUpdatedPost);

        PostDTO updatedPost = service.updatePost(updatedPostDTO);

        assertThat(updatedPost.getTitle()).isEqualTo(newTitle);
        assertThat(updatedPost.getContent()).isEqualTo(newContent);

        verify(repository).save(any(Post.class));
    }

    @Test
    void givenPost_whenDelete_thenSuccess(){
        Long postId = 1L;
        PostDTO postDTO = PostDTO.builder()
                .id(postId)
                .build();
        Post post = Post.builder()
                .id(postId)
                .build();

        when(repository.findById(postId)).thenReturn(Optional.of(post));

        service.deletePost(postDTO);

        verify(repository).delete(post);
    }


    @Test
    void givenNothing_whenFindAllPosts_thenReturnPosts() {
        Post post1 = Post.builder()
                .id(1L)
                .title("Post 1")
                .content("Content 1")
                .build();

        Post post2 = Post.builder()
                .id(2L)
                .title("Post 2")
                .content("Content 2")
                .build();

        when(repository.findAll()).thenReturn(Arrays.asList(post1, post2));

        List<PostDTO> foundPosts = service.findAllPosts();

        assertEquals(2, foundPosts.size());
        assertEquals(post1.getTitle(), foundPosts.get(0).getTitle());
        assertEquals(post2.getTitle(), foundPosts.get(1).getTitle());

        verify(repository, times(1)).findAll();
    }
}
