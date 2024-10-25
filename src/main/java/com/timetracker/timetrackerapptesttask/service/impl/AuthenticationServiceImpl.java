package com.timetracker.timetrackerapptesttask.service.impl;

import com.timetracker.timetrackerapptesttask.controller.*;
import com.timetracker.timetrackerapptesttask.dto.*;
import com.timetracker.timetrackerapptesttask.entity.*;
import com.timetracker.timetrackerapptesttask.entity.User;
import com.timetracker.timetrackerapptesttask.exception.*;
import com.timetracker.timetrackerapptesttask.repository.*;
import com.timetracker.timetrackerapptesttask.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.*;

import org.springframework.stereotype.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

/**
 * Сервис для управления авторизацией и аутентификацией пользователей.
 */
@Service
public class AuthenticationServiceImpl implements IAuthenticationService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private  JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private  TokenRepository tokenRepository;

    @Autowired
    private  IControlSecurityExpression customSecurityExpression;

    /**
     * Регистрирует нового пользователя.
     *
     * @param request Запрос на регистрацию.
     * @return Ответ с JWT-токеном для аутентификации.
     * @throws UserWithSuchEmailAlreadyExistsException Если пользователь с такой почтой уже существует.
     */
    @Override
    public AuthenticationResponse register(RegisterRequest request)throws UserWithSuchEmailAlreadyExistsException {
// Поиск пользователя по электронной почте
        Optional<User> userOptional = repository.findByEmail(request.getEmail());

        if(userOptional.isEmpty()){
            // Если пользователя с такой почтой не существует, создаем нового пользователя
            var user  = User.builder()
                    .name(request.getName())
                    .lastname(request.getLastname())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .rolePriority(RoleDto.ADMIN.getPriority())
                    .build();


            // Сохраняем пользователя в репозитории
            var savedUser = repository.save(user);
            // Генерируем JWT-токен для аутентификации
            var jwtToken = jwtService.generateToken(user);

            // Сохраняем токен пользователя
            saveUserToken(savedUser, jwtToken);
            // Возвращаем ответ с токено
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        }else{
            // Если пользователь с такой почтой уже существует, выбрасываем исключение
            throw new UserWithSuchEmailAlreadyExistsException();
        }
    }
    /**
     * Сохраняет токен пользователя.
     *
     * @param user      Пользователь, для которого сохраняется токен.
     * @param jwtToken  JWT-токен для аутентификации.
     */
    private void saveUserToken(User user, String jwtToken){
        // Создаем объект токена
        var token  = Token.builder()
                .user(user)
                .token(jwtToken)
                .revoked(false)
                .expired(false)
                .build();
        // Сохраняем токен в репозитории
        tokenRepository.save(token);
    }
    /**
     * Аутентифицирует пользователя.
     *
     * @param request Запрос на аутентификацию.
     * @return Ответ с JWT-токеном для аутентификации.
     */
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Проверяем аутентификацию пользователя
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
// Находим пользователя по электронной почте
        var user = repository.findByEmail(request.getEmail()).orElseThrow();

        // Устанавливаем приоритет роли в зависимости от указанной роли в запросе
        if(request.getRole().equals(RoleDto.ADMIN.name())){
            user.setRolePriority(RoleDto.ADMIN.getPriority());
        }else{
            user.setRolePriority(RoleDto.USER.getPriority());
        }
        // Сохраняем пользователя
        repository.save(user);
        // Генерируем JWT-токен для аутентификации
        var jwtToken = jwtService.generateToken(user);
        // Отзываем все предыдущие токены пользователя
        revokeAllUserTokens(user);
        // Сохраняем новый токен пользователя
        saveUserToken(user,jwtToken);
        // Возвращаем ответ с токеном
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    /**
     * Отзывает все токены пользователя.
     *
     * @param user Пользователь, для которого отзываются токены.
     */
    private void revokeAllUserTokens(User user){
        // Находим все действующие токены пользователя
        var validTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if(validTokens.isEmpty()){
            // Если нет действующих токенов, завершаем метод
            return;
        }else{
            // Отзываем каждый действующий токен
            validTokens.forEach(t->{

                t.setExpired(true);
                t.setRevoked(true);
            });
            // Сохраняем изменения в репозитории
            tokenRepository.saveAll(validTokens);
        }
    }






}
