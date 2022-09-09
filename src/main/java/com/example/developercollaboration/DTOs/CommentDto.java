package com.example.developercollaboration.DTOs;

import java.io.Serializable;

public record CommentDto(Long id, String userUsername, Long projectId, String projectProjectName,
                         String comment) implements Serializable {
}