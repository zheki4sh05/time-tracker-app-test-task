package com.timetracker.timetrackerapptesttask.service;

import com.timetracker.timetrackerapptesttask.controller.*;

public interface IAuthenticationService {

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
