package com.timetracker.timetrackerapptesttask.repository;

import com.timetracker.timetrackerapptesttask.entity.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

import java.util.*;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {


    @Query("""

select p
from Participation p 
where p.user.id= :userId

""")
    List<Participation> findAllByUserId(@Param("userId") Long userId);



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
    Optional<Participation> getByUserId(@Param("userId") Long userId, @Param("projectId") Long projectId, @Param("priority") Integer Param);


        @Query("""
    
    select p
    from Participation p 
    where p.user.id= :userId and p.project.id= :projectId
    
    """)
    Participation findByProjectAndUserId(@Param("projectId") Long projectId,@Param("userId") Long id);
}
