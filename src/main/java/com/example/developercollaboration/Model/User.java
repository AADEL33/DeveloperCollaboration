package com.example.developercollaboration.Model;

import com.example.developercollaboration.Enums.RolesEnum;
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
    private String ResetPasswordToken;

    private boolean isProfessional;


   @ElementCollection(targetClass = RolesEnum.class)
   @JoinTable(name = "tblroles", joinColumns = @JoinColumn(name = "user_id"))
   @Column(name = "roles", nullable = false)
   @Enumerated(EnumType.STRING)
   private Collection<RolesEnum> roles;


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