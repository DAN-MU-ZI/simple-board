package com.project.springboard.domain.Post;

import com.project.springboard.domain.Comment.Comment;
import com.project.springboard.domain.User.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table
@Entity
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	private String content;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
	private final List<Comment> comments = new ArrayList<>();

	@Builder
	public Post(final Long id, final String title, final String content, final User user) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.user = user;
	}
}
