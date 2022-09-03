package com.example.developercollaboration.Repositories;
import com.example.developercollaboration.Model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {
    List<Project> findAllByIsOpenTrue();
    List<Project> findAllByIsOpenFalse();
    Project findProjectById(Long id);

 /*   @Query("select P from Project as P where containsAll (collect(P.requiredSkills),skills)")
    List<Project> findAllByReq(List<Skill>skills);*/


}