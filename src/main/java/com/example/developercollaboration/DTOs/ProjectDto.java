package com.example.developercollaboration.DTOs;

import java.io.Serializable;
import java.util.List;

public record ProjectDto(Long id, String projectName, Boolean isOpen,
                         List<String> commentComments) implements Serializable {
}