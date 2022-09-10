package com.example.developercollaboration.Exceptions.CommentExceptions;

public class CantDeleteCommentException extends IllegalAccessException{
    public CantDeleteCommentException(){
        super("this project does not contains this comment or maybe you do not own this comment");
    }
}