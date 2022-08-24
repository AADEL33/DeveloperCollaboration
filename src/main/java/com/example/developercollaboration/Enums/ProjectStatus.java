package com.example.developercollaboration.Enums;

public enum ProjectStatus {
    NOT_STARTED("Not started"),
    STARTED("Started"),
    IN_PROGRESS("In progress"),
    DONE("Done");
    private final String status;
    ProjectStatus(String status) {
        this.status =status;
    }

    public String getSkill() {
        return status;
    }
}