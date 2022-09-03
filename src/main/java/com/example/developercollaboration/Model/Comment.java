package com.example.developercollaboration.Model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name="project_id")
    private Project project;
    @Column(nullable = false)
    private String comment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment comment)) return false;
        return Objects.equals(getId(), comment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}