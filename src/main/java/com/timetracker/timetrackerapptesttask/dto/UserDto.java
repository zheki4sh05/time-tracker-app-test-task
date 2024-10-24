package com.timetracker.timetrackerapptesttask.dto;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.*;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {



        private String name;

        private String lastname;

        private String email;





}
