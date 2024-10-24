package com.timetracker.timetrackerapptesttask.controller;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String lastname;
    private String name;
    private String email;
    private String password;
}