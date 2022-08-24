package com.example.developercollaboration.Repositories;

import com.example.developercollaboration.Model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends JpaRepository<Skill,String> {

}