package com.timetracker.timetrackerapptesttask.entity;


import jakarta.persistence.*;
import lombok.*;
/**
 * Сущность, представляющая участие пользователя в проекте.
 */
@Entity
@Table(name="participation")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role")
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project")
    private Project project;
}
