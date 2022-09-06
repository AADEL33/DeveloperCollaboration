package com.example.developercollaboration.DTOs;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommentDto implements Serializable {
    private final Long id;
    private final UserDto user;
    private final String comment;
}