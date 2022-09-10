package com.example.developercollaboration.Exceptions.SkillExceptions;


public class SkillAlreadyExistException extends RuntimeException {
    public SkillAlreadyExistException(){
        super("The skill you trying to add already exists ");
    }
}