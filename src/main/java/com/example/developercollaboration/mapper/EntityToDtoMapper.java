package com.example.developercollaboration.mapper;

import com.example.developercollaboration.DTOs.CommentDto;
import com.example.developercollaboration.DTOs.ProjectDto;
import com.example.developercollaboration.DTOs.UserDto;
import com.example.developercollaboration.Model.Comment;
import com.example.developercollaboration.Model.Project;
import com.example.developercollaboration.Model.User;

import java.util.stream.Collectors;

public class EntityToDtoMapper {
    public static CommentDto commentToCommentDto(Comment comment){
        return new CommentDto(
                comment.getId(),
                comment.getUser().getUsername(),
                comment.getProject().getId(),
                comment.getProject().getProjectName(),
                comment.getComment()
                );
    }
    public static UserDto UserToUserDto(User user){
            return new UserDto(user.getFirstname(), user.getLastname(), user.getUsername());
    }
    public static ProjectDto ProjectToProjectDto(Project project){
        return new ProjectDto(project.getId(),project.getProjectName(),project.getIsOpen(),project.getComments().stream().map(comment->comment.getComment()).collect(Collectors.toList()));
    }
}