package com.example.developercollaboration.Exceptions.ProjectExceptions;

public class NoProjectRequiringAtLeastOneSkillException extends RuntimeException{
    public NoProjectRequiringAtLeastOneSkillException(){
        super("No project requiring at least one of the giving skills");
    }
}