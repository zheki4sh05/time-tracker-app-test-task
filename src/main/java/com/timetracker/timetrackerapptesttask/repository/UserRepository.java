package com.timetracker.timetrackerapptesttask.repository;

import com.timetracker.timetrackerapptesttask.entity.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

import java.util.*;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
select u
from User u
where u.email= :email
""")
    Optional<User> findByEmail(@Param("email") String email);
}
