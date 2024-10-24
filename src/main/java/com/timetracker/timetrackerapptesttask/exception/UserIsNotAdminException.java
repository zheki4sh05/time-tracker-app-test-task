package com.timetracker.timetrackerapptesttask.exception;

public class UserIsNotAdminException extends RuntimeException{
    public UserIsNotAdminException() {
        super("User hasn't rights to add user into project");
    }
}
