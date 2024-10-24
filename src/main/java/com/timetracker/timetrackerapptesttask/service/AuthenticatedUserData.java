package com.timetracker.timetrackerapptesttask.service;

import com.timetracker.timetrackerapptesttask.dto.*;
import com.timetracker.timetrackerapptesttask.entity.*;

import java.util.*;

public interface AuthenticatedUserData {

    Optional<User> getUserByEmail(String email);

    String getCurrentUserEmail();

    User getCurrentUser();

    UserDto mapFrom(User addUser);
}
