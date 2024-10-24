package com.timetracker.timetrackerapptesttask.dto;

public enum RoleDto {

    ADMIN (1),
    USER (2);
    private Integer priority;

    RoleDto(Integer priority) {
        this.priority = priority;
    }

    public Integer getPriority() {
        return priority;
    }

}
