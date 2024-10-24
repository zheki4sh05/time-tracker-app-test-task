package com.timetracker.timetrackerapptesttask.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddUserProjectDto {

    private String email;
    private Long projectId;

}
