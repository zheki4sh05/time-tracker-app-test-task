package com.timetracker.timetrackerapptesttask.controller;

import com.timetracker.timetrackerapptesttask.exception.*;
import com.timetracker.timetrackerapptesttask.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/auth")
public class AuthorizationController {

    @Autowired
    private IAuthenticationService service; //сервис для управления аутентификацией и авторизацией пользователей

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request){

        try{
            return ResponseEntity.ok(service.register(request));
        }catch (UserWithSuchEmailAlreadyExistsException e){
            return new ResponseEntity<>("", HttpStatus.CONFLICT);
        }

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(service.authenticate(request));
    }

}
