package com.example.developercollaboration.Exceptions.UserExceptions;

import java.util.NoSuchElementException;

public class UserNotFoundException extends NoSuchElementException {
    public UserNotFoundException(){
        super("Sorry, We cen not find the targeted user");
    }
}