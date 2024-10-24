package com.timetracker.timetrackerapptesttask.controller;

import com.timetracker.timetrackerapptesttask.dto.*;
import com.timetracker.timetrackerapptesttask.entity.*;
import com.timetracker.timetrackerapptesttask.facade.*;
import com.timetracker.timetrackerapptesttask.service.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NonUniqueResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor
public class ProjectController {

    private final IProjectControl projectControl;
    private final IProjectUserCredentialsFacade projectUserCredentialsFacade;

    @PostMapping("/create")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> createProject(@RequestHeader Map<String, String> headers,
                                           @RequestBody ProjectDto createProjectDTO) {
        try {
           // Integer id = projectControl.createNewProject(createProjectDTO);
            Long id = projectUserCredentialsFacade.createNewProject(createProjectDTO);

            return ResponseEntity.ok(id);
        } catch (EntityNotFoundException e) { //
            return new ResponseEntity<>("",HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> deleteProject(@RequestHeader Map<String, String> headers,
                                                @PathVariable Long id) {
        try{
            projectControl.deleteProject(id);
            return ResponseEntity.ok(id);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>("",HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping ("/")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> updateProject(@RequestHeader Map<String, String> headers,
                                                @RequestBody ProjectDto projectUpdateDto) {
        try {
        ProjectDto projectDto =  projectControl.updateProject(projectUpdateDto);
            return ResponseEntity.ok(projectDto);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("",HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/user")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> getUserProjects(@RequestHeader Map<String, String> headers) {

        try {

            List<ProjectDto> projects =  projectUserCredentialsFacade.getAuthenticatedUserProjects();

           // List<ProjectDto> projects = projectControl.getAllProjects(userId);
            return ResponseEntity.ok(projects);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("",HttpStatus.NOT_FOUND);
        }

    }
    @CrossOrigin
    @GetMapping("/{id}/workers")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> getProjectWorkers(@RequestHeader Map<String, String> headers,
                                               @RequestParam(value = "id") Long projectId) {

        try {
            List<UserRoleInProjectDto> projects = projectControl.getAllWorkers(projectId);
            return new ResponseEntity<>(projects, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("",HttpStatus.NOT_FOUND);
        }

    }

    @CrossOrigin
    @PostMapping("/add")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> addUserToProject(@RequestHeader Map<String, String> headers,
                                               @RequestBody AddUserProjectDto addUserProjectDto) {

        try {

            UserDto user = projectUserCredentialsFacade.addUser(addUserProjectDto);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("",HttpStatus.NOT_FOUND);
        }

    }

    @CrossOrigin
    @DeleteMapping("/{email}")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> deleteUserFromProject(@RequestHeader Map<String, String> headers,
                                              @PathVariable(value = "email") String email) {

        try {
            projectUserCredentialsFacade.deleteByAuthenticatedUser(email);
            return new ResponseEntity<>("", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("",HttpStatus.NOT_FOUND);
        }

    }


}