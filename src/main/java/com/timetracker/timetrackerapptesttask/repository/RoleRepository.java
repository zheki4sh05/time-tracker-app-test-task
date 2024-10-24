package com.timetracker.timetrackerapptesttask.repository;

import com.timetracker.timetrackerapptesttask.entity.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.jpa.repository.*;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("""

    select r
    from Role r
    where r.priority= :p

""")
    Role getWithPriority(@Value("p") Integer p);
}
