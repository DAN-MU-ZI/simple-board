package com.project.springboard.repository;

import com.project.springboard.domain.Post.Post;
import com.project.springboard.domain.User.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findAllByUser(User user);
}
