package com.example.developercollaboration.Exceptions.UserExceptions;

public class ResetPasswordFailedException extends IllegalAccessException{
    public ResetPasswordFailedException(){
        super("You are not allowed to perform this operation");
    }
}