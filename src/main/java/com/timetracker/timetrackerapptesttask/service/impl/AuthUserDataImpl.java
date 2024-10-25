package com.timetracker.timetrackerapptesttask.service.impl;

import com.timetracker.timetrackerapptesttask.dto.*;
import com.timetracker.timetrackerapptesttask.entity.*;
import com.timetracker.timetrackerapptesttask.repository.*;
import com.timetracker.timetrackerapptesttask.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Сервис для получения автооризованных пользователй.
 */
@Service
public class AuthUserDataImpl implements AuthenticatedUserData {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IControlSecurityExpression customSecurityExpression;

    //возвращает пользователя по его адресу электронной почты
    @Override
    public Optional<User> getUserByEmail(String email) {

        return userRepository.findByEmail(email);
    }
    //возвращает адрес электронной почты текущего пользователя.
    @Override
    public String getCurrentUserEmail() {

        return customSecurityExpression.getPrincipal().getEmail();

    }
    //возвращает объект текущего пользователя.
    @Override
    public User getCurrentUser() {
        return customSecurityExpression.getPrincipal();
    }
    //преобразует объект пользователя в DTO (UserDto), заполняя его поля.
    @Override
    public UserDto mapFrom(User addUser) {
        return UserDto.builder()
                .name(addUser.getName())
                .lastname(addUser.getLastname())
                .email(addUser.getEmail())
                .build();
    }
}
