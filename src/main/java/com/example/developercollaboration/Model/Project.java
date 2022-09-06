package com.example.developercollaboration.Model;

import com.example.developercollaboration.Enums.ProjectStatus;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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
    @JoinColumn(name ="creator_id")
    private User creator;
    @ManyToMany(fetch=FetchType.LAZY)@JoinTable(
            name="project_user",
            joinColumns = {@JoinColumn(name="project_id")},
            inverseJoinColumns = {@JoinColumn(name="user_id")}
    )
   private List<User>  contributors=new ArrayList<>();
    @Column(nullable = false)
   private String projectName;
    @ManyToMany(fetch=FetchType.LAZY)@JoinTable(
            name="project_skill",
            joinColumns = {@JoinColumn(name="project_id")},
            inverseJoinColumns = {@JoinColumn(name="skill_id")}
    )
    private List<Skill>  requiredSkills=new ArrayList<>();
    @Column(nullable = false)
    private String description;
    private ProjectStatus projectstatus;
    private String gtihubLink;
    private int maxContributors;
    @Column(columnDefinition = "boolean default true")
    private Boolean isOpen=true;
    @OneToMany(mappedBy="project",fetch = FetchType.LAZY)
    private List<Comment>  comments;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project project)) return false;
        return Objects.equals(getId(), project.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}