package com.example.developercollaboration.Exceptions.ProjectExceptions;

import java.util.NoSuchElementException;

public class NoProjectRequiringAllSkillsException extends NoSuchElementException {
    public NoProjectRequiringAllSkillsException(){
        super("No project requiring all mentioned skills");
    }
}