package com.timetracker.timetrackerapptesttask.repository;

import com.timetracker.timetrackerapptesttask.entity.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {


    @Query("""

select p
from Participation p 
where p.user.id= :userId

""")
    List<Participation> findAllByUserId(@Value("userId") Long userId);



    @Query("""

select p
from Participation p 
where p.project.id= :projectId

""")
    List<Participation> getWorkersBy(Long projectId);



    @Query("""

select p
from Participation p 
where p.user.id= :userId and p.role.priority= :priority and p.project.id= :projectId

""")
    Optional<Participation> getByUserId(@Value("userId") Long userId,@Value("projectId") Long projectId, @Value("priority") Integer value);
}
