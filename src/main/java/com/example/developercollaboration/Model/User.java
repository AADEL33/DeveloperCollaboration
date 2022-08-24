package com.example.developercollaboration.Model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(
            strategy = AUTO
    )
    private Long Id;
    @Column(
            nullable = false
    )
    private String firstname;

    @Column(
            nullable = false
    )
    private String lastname;
    @Column(
            nullable = false
    )

    private String username;
    @Column(
            nullable = false
    )
    private String password;

    private boolean isProfessional;

    @ManyToMany(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JoinTable(

            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<Role> roles = new ArrayList<>();

    @Column(
    )
    private Date dateOfBirth;

    @ManyToMany(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JoinTable(
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private List<Skill> skills = new ArrayList<>();
    @Column(
    )
    private String githubLink;
}