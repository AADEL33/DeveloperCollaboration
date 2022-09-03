package com.example.developercollaboration.Model;

import com.example.developercollaboration.Enums.RolesEnum;
import lombok.*;

import javax.persistence.*;
import java.util.*;



@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter
public class User {
    @Id
    private String username;
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
    private String password;

    private String resetPasswordToken;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(getUsername(), user.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername());
    }
}