package com.project.springboard.repository;

import com.project.springboard.domain.Comment.Comment;
import com.project.springboard.domain.User.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findAllByUser(User user);
}
