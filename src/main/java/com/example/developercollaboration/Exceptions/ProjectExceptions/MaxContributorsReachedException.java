package com.example.developercollaboration.Exceptions.ProjectExceptions;

public class MaxContributorsReachedException extends RuntimeException{
    public MaxContributorsReachedException(){
        super("Sorry you can not join this project, the limit number of contributors is already reached");
    }
}