package com.timetracker.timetrackerapptesttask.service;

import com.timetracker.timetrackerapptesttask.entity.*;

import java.util.*;

public interface IControlSecurityExpression {

    User getPrincipal();
    boolean canAccessUser(Map<String, String> headers);
    String getUserEmail(Map<String, String> headers);

}
