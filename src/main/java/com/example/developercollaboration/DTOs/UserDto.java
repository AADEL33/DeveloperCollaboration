package com.example.developercollaboration.DTOs;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {
    private final String username;
    private final String firstname;
    private final String lastname;
}