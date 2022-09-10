package com.example.developercollaboration.Exceptions.CommentExceptions;

import java.util.NoSuchElementException;

public class CommentNotFoundException extends NoSuchElementException {
    public CommentNotFoundException(){
        super("No comment found");
    }
}