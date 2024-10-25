package com.timetracker.timetrackerapptesttask.repository;

import com.timetracker.timetrackerapptesttask.entity.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("""

    select r
    from Role r
    where r.priority= :p

""")
    Role getWithPriority(@Param("p") Integer p);
}
