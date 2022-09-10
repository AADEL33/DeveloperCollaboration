package com.example.developercollaboration.Exceptions.SkillExceptions;

import java.util.NoSuchElementException;

public class SkillNotFoundException extends NoSuchElementException {
    public SkillNotFoundException(){
        super("Sorry ,we can not find the targeted skill");
    }
}