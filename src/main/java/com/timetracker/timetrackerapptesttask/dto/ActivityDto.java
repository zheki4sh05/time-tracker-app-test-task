package com.timetracker.timetrackerapptesttask.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDto {

    private Long id;
    private String name;
    private String startAt;
    private String finishedAt;
    private Long projectId;
}
