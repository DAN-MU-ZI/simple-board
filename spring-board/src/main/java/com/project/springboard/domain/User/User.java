package com.project.springboard.domain.User;

import com.project.springboard.domain.Post.Post;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SQLDelete(sql = "UPDATE user SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@ToString
public class User {

	@OneToMany(mappedBy = "user")
	private final List<Post> posts = new ArrayList<>();
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, unique = true)
	private String email;

	@Column
	@ColumnDefault("false")
	private boolean deleted;

	@Builder
	public User(final Long id, final String email, final boolean deleted) {
		this.id = id;
		this.email = email;
		this.deleted = deleted;
	}
}
