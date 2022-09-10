package com.example.developercollaboration.Exceptions.ProjectExceptions;

import java.util.NoSuchElementException;

public class UserNotMemberException extends NoSuchElementException {
    public UserNotMemberException(){
        super("this user is not a project member");
    }
}