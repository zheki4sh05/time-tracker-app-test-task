package com.timetracker.timetrackerapptesttask.repository;

import com.timetracker.timetrackerapptesttask.entity.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

import java.util.*;


public interface ActivityRepository extends JpaRepository<Activity,Long> {


    @Query("""
select o 
from Activity o 
where o.id= :id and o.project.id= :projectId

""")
    Optional<Activity> findById(@Param("id")Long id, @Param("projectId")Long projectId);

            @Query("""
        select o 
        from Activity o 
        where o.id= :id
        
        """)
    Optional<Activity> findById(@Param("id") Long id);


    @Query("""
        select o 
        from Activity o 
        where o.project.id= :projectId
        
        """)
    Optional<List<Activity>> getAllByProject(@Param("projectId") Long projectId);
}
