package com.example.developercollaboration.Repositories;
import com.example.developercollaboration.Model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {
    List<Project> findAllByIsOpenTrue();
    List<Project> findAllByIsOpenFalse();
    Optional<Project>  findProjectById(Long id);

}