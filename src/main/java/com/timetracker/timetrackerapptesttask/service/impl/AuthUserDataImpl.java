package com.timetracker.timetrackerapptesttask.service.impl;

import com.timetracker.timetrackerapptesttask.dto.*;
import com.timetracker.timetrackerapptesttask.entity.*;
import com.timetracker.timetrackerapptesttask.repository.*;
import com.timetracker.timetrackerapptesttask.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class AuthUserDataImpl implements AuthenticatedUserData {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IControlSecurityExpression customSecurityExpression;
    @Override
    public Optional<User> getUserByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    @Override
    public String getCurrentUserEmail() {

        return customSecurityExpression.getPrincipal().getEmail();

    }

    @Override
    public User getCurrentUser() {
        return customSecurityExpression.getPrincipal();
    }

    @Override
    public UserDto mapFrom(User addUser) {
        return UserDto.builder()
                .name(addUser.getName())
                .lastname(addUser.getLastname())
                .email(addUser.getEmail())
                .build();
    }
}
