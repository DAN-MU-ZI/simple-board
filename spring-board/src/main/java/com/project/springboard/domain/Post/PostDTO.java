package com.project.springboard.domain.Post;

import com.project.springboard.domain.User.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NoArgsConstructor.AnyAnnotation;

@NoArgsConstructor
@Getter
public class PostDTO {
    private Long id;
    private String title;
    private String content;

    @Builder
    public PostDTO(final Long id, final String title, final String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public static PostDTO from(Post post){
        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }
}
