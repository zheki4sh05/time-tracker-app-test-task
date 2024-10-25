package com.timetracker.timetrackerapptesttask.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.*;

/**
 * Сущность, представляющая активность в проекте.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "activity")
@Builder
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name = "started")
    private Timestamp started;

    @Column(name = "finished")
    private Timestamp finished;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker")
    private User worker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project")
    private Project project;



}
