package com.example.developercollaboration.Model;

import com.example.developercollaboration.Enums.ProjectStatus;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter
public class Project  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name ="user_id")
    private User creator;
    @ManyToMany(fetch=FetchType.LAZY)@JoinTable(
            name="project_user",
            joinColumns = {@JoinColumn(name="project_id")},
            inverseJoinColumns = {@JoinColumn(name="user_id")}
    )
   private List<User>  contributors=new ArrayList<>();
   private String projectName;
    @ManyToMany(fetch=FetchType.LAZY)@JoinTable(
            name="project_skill",
            joinColumns = {@JoinColumn(name="project_id")},
            inverseJoinColumns = {@JoinColumn(name="skill_id")}
    )
    private List<Skill>  requiredSkills=new ArrayList<>();

    private String description;
    private ProjectStatus projectstatus;
    private String gtihubLink;

}