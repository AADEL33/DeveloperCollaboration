package com.example.developercollaboration.DTOs;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProjectDto implements Serializable {
    private final Long id;
    private final UserDto creator;
    private final List<UserDto> contributors;
    private final String projectName;
}