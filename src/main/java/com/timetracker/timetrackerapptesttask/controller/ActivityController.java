package com.timetracker.timetrackerapptesttask.controller;

import com.timetracker.timetrackerapptesttask.dto.*;
import com.timetracker.timetrackerapptesttask.exception.*;
import com.timetracker.timetrackerapptesttask.facade.*;
import com.timetracker.timetrackerapptesttask.service.*;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/activity")
@RequiredArgsConstructor
public class ActivityController {



    @Autowired
    private IActivityControl activityControl; //сервис для управления активностями пользователей в рамках какого-то проекта

    @Autowired
    private IProjectActivityFacade projectActivityFacade;// фасад необходимый для взаимодействия сервисов для управления проектами и активностями

    @PostMapping("/create")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)") //
    public ResponseEntity<?> createActivity(@RequestHeader Map<String, String> headers,
                                           @RequestBody CreateActivityDto createActivityDto) {
        try {


            Long id = projectActivityFacade.createActivity(createActivityDto);

            return ResponseEntity.ok(id);

        } catch (ProjectNotFoundException e) { //
            return new ResponseEntity<>("",HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/start")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> startActivity(@RequestHeader Map<String, String> headers,
                                        @RequestBody ActivityDto activityDto) {
        try {

            String time = activityControl.startActivity(activityDto);

            return ResponseEntity.ok(time);
        } catch (EntityNotFoundException e) { //
            return new ResponseEntity<>("",HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/finish")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> finishActivity(@RequestHeader Map<String, String> headers,
                                        @RequestBody ActivityDto activityDto) {
        try {
            // Integer id = projectControl.createNewProject(createProjectDTO);
            String time = activityControl.finishActivity(activityDto);

            return ResponseEntity.ok(time);
        } catch (EntityNotFoundException e) { //
            return new ResponseEntity<>("",HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/item")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> deleteActivity(@RequestHeader Map<String, String> headers,
                                         @RequestParam Long id) {
        try {
             activityControl.deleteActivity(id);


            return ResponseEntity.ok(id);
        } catch (EntityNotFoundException e) { //
            return new ResponseEntity<>("",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/items")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> getByProject(@RequestHeader Map<String, String> headers,
                                         @RequestParam("projectId") Long projectId) {
        try {

            List<ActivityDto> activityDtoList = activityControl.getAllByProject(projectId);

            return ResponseEntity.ok(activityDtoList);
        } catch (EntityNotFoundException e) { //
            return new ResponseEntity<>("",HttpStatus.NOT_FOUND);
        }
    }

}
