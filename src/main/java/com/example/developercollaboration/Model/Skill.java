package com.example.developercollaboration.Model;

import lombok.*;

import javax.persistence.*;


@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter
public class Skill {
    @Id
    @Column( nullable = false)
    private String name;
    private String description;
}