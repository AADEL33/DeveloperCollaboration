package com.example.developercollaboration.DTOs;

import java.io.Serializable;

public record UserDto(String username, String firstname, String lastname) implements Serializable {
}