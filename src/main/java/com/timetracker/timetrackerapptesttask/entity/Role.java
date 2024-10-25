package com.timetracker.timetrackerapptesttask.entity;

import jakarta.persistence.*;
import lombok.*;
/**
 * Сущность, представляющая роль пользователя в каком-то проекте
 */
@Entity
@Table(name="role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "priority")
    private Integer priority;
}
