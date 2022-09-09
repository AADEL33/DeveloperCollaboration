package com.example.developercollaboration.Repositories;

import com.example.developercollaboration.DTOs.CommentDto;
import com.example.developercollaboration.Model.Comment;
import com.example.developercollaboration.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository  extends JpaRepository<Comment,Long> {
   @Query(value = "SELECT new com.example.developercollaboration.DTOs.CommentDto(cso.id,cso.user.username,cso.project.id,cso.project.projectName,cso.comment) FROM Comment cso where cso.user=?1")
   List<CommentDto> findAllByUser(@Param("user") User user);
}