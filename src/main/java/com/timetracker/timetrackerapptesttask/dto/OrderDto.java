package com.timetracker.timetrackerapptesttask.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private String name;
    private String startAt;
    private String finishedAt;
    private Long projectId;
}
