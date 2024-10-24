package com.timetracker.timetrackerapptesttask.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRoleInProjectDto {

    private String name;

    private String lastname;

    private String email;

    private Integer priority;

}
