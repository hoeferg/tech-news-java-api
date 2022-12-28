package com.technews.technewsjavaapi.model.repository;

import com.technews.technewsjavaapi.model.Comment;
import com.technews.technewsjavaapi.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository <Comment, Integer> {
    List<Comment> findAllByPostId( int postId);
}
