package com.example.developercollaboration.Exceptions.ProjectExceptions;

public class AlreadyMemberException extends RuntimeException{
    public AlreadyMemberException(){
        super("you are already member");
    }
}