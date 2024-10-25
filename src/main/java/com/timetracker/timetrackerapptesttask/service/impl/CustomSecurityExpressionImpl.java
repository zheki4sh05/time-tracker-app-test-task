package com.timetracker.timetrackerapptesttask.service.impl;

import com.timetracker.timetrackerapptesttask.entity.*;
import com.timetracker.timetrackerapptesttask.service.*;
import lombok.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service("cse")
@RequiredArgsConstructor
public class CustomSecurityExpressionImpl implements IControlSecurityExpression {

    private final JwtService jwtService;

    public boolean canAccessUser(Map<String, String> headers) {

        User user = getPrincipal();

        Boolean isAccessed = getUserEmail(headers).equals(user.getEmail());


        return isAccessed;
    }
    public User getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        return (User) authentication.getPrincipal();
    }
    public String getUserEmail(Map<String, String> headers){
        return jwtService.extractUserEmail(headers.get("authorization").split(" ")[1]);
    }

}

