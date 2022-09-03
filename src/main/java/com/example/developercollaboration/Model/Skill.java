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
public class Skill {
    @Id
    @Column( nullable = false)
    private String name;
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Skill skill)) return false;
        return Objects.equals(getName(), skill.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}