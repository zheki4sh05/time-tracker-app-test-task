package com.timetracker.timetrackerapptesttask.controller;

import com.timetracker.timetrackerapptesttask.dto.*;
import com.timetracker.timetrackerapptesttask.service.*;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class OrderController {

    @Autowired
    private IOrderControl orderControl;

    @PostMapping("/create")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> startOrder(@RequestHeader Map<String, String> headers,
                                           @RequestBody OrderDto orderDto) {
        try {
            // Integer id = projectControl.createNewProject(createProjectDTO);
            Long id = orderControl.startNewOrder(orderDto);

            return ResponseEntity.ok(id);
        } catch (EntityNotFoundException e) { //
            return new ResponseEntity<>("",HttpStatus.NOT_FOUND);
        }
    }

}
