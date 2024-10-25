package com.timetracker.timetrackerapptesttask.dto;

import lombok.*;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CreateActivityDto {
    private Long projectId;
    private String name;
}
