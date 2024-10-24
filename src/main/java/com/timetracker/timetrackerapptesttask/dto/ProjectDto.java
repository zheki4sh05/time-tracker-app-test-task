package com.timetracker.timetrackerapptesttask.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectDto {


    private Long id;
    private String name;
    private String desc;
    private String role;
    private Integer rolePriority;

}
