package com.example.developercollaboration.Enums;

public enum SkillEnum {
    Java("Java"),
    JavaScript("JavaScript"),
    Python("Python"),
    C("C"),
    PHP("Php"),
    ReactJs("ReactJs"),
    Node_Js("NodeJs"),
    Cplusplus("C++");
    private final String Skill;
    SkillEnum(String Skill) {
        this.Skill =Skill;
    }

    public String getSkill() {
        return Skill;
    }
}