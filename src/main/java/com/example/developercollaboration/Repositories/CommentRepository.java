package com.example.developercollaboration.Repositories;

import com.example.developercollaboration.Model.Comment;
import com.example.developercollaboration.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository  extends JpaRepository<Comment,Long> {
   List<Comment> findCommentsByUser(User user);
}