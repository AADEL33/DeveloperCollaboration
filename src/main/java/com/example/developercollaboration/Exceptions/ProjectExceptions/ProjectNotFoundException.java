package com.example.developercollaboration.Exceptions.ProjectExceptions;

import java.util.NoSuchElementException;

public class ProjectNotFoundException extends NoSuchElementException {
    public ProjectNotFoundException(){
        super("Project not found");
    }
}