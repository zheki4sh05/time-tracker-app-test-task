package com.timetracker.timetrackerapptesttask.role;

import com.timetracker.timetrackerapptesttask.dto.*;
import com.timetracker.timetrackerapptesttask.entity.*;
import lombok.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;

import java.util.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRole extends User{

    RoleDto roleDto;

    public UserRole(RoleDto roleDto,User user) {
        super(user.getId(), user.getName(), user.getLastname(), user.getEmail(),user.getPassword(), roleDto.getPriority());
        this.roleDto = roleDto;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(roleDto.name()));
    }


}
