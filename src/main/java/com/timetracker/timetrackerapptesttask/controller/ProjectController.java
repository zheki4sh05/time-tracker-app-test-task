package com.timetracker.timetrackerapptesttask.controller;

import com.timetracker.timetrackerapptesttask.dto.*;
import com.timetracker.timetrackerapptesttask.facade.*;
import com.timetracker.timetrackerapptesttask.service.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor
public class ProjectController {

    private final IProjectControl projectControl; //сервис для управления проектами
    private final IProjectUserCredentialsFacade projectUserCredentialsFacade; // фасад для связывания логики работы сервиса пользователей и проекты


    @PostMapping("/create")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> createProject(@RequestHeader Map<String, String> headers,
                                           @RequestBody ProjectDto createProjectDTO) {
        try {
            Long id = projectUserCredentialsFacade.createNewProject(createProjectDTO);

            return ResponseEntity.ok(id);
        } catch (EntityNotFoundException e) { //
            return new ResponseEntity<>("",HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/items")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> deleteProject(@RequestHeader Map<String, String> headers,
                                                @RequestParam("id") Long id) {
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

            ProjectDto projectDto = projectUserCredentialsFacade.updateProject(projectUpdateDto);
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

            return ResponseEntity.ok(projects);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("",HttpStatus.NOT_FOUND);
        }

    }
    @CrossOrigin
    @GetMapping("/{id}/workers")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> getProjectWorkers(@RequestHeader Map<String, String> headers,
                                               @PathVariable(value = "id") String projectId) {

        try {
            List<UserRoleInProjectDto> projects = projectControl.getAllWorkers(Long.parseLong(projectId));
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
    @DeleteMapping("/user")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> deleteUserFromProject(@RequestHeader Map<String, String> headers,
                                              @RequestParam(value = "email") String email,
                                                   @RequestParam(value = "projectId") Long projectId) {

        try {
            projectUserCredentialsFacade.deleteByAuthenticatedUser(email,projectId);
            return new ResponseEntity<>("", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("",HttpStatus.NOT_FOUND);
        }

    }


}