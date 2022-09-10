package com.example.developercollaboration.Exceptions.UserExceptions;

import com.example.developercollaboration.Exceptions.ProjectExceptions.AlreadyMemberException;

public class AlraedyHaveAccountException extends RuntimeException {
    public AlraedyHaveAccountException(){
        super("A user with this email already exists ");
    }
}