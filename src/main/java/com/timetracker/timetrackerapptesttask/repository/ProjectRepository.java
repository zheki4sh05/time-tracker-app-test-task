package com.timetracker.timetrackerapptesttask.repository;

import com.timetracker.timetrackerapptesttask.entity.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {


}
