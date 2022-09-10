package com.example.developercollaboration.Exceptions.UserExceptions;

import javax.mail.internet.AddressException;

public class EmailNotValidException extends AddressException {
    public EmailNotValidException(){
        super("Your email is not valid");
    }
}