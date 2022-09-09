package com.example.developercollaboration.Controller;

import com.example.developercollaboration.DTOs.ProjectDto;
import com.example.developercollaboration.Model.Project;
import com.example.developercollaboration.Model.Skill;
import com.example.developercollaboration.Service.ProjectService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Data
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping("/findProjectByid")
    public Optional<ProjectDto>  findProjectById(@RequestParam Long id) throws Exception {
        return projectService.findById(id);
    }

    @PostMapping("/add")
    public void addProject(@RequestBody Project project){
        projectService.announceProject(project);
    }

    @DeleteMapping("/delete")
    public void deleteProject(@RequestParam Long id) throws Exception {
        projectService.deleteProjectById(id);
    }

    @GetMapping("/openProjects")
    public List<ProjectDto> findOpenProjects() throws Exception {
       return projectService.findAllOpenProjects();
    }

    @GetMapping("/closedProjects")
    public List<ProjectDto> findClosedProjects() throws Exception {
        return projectService.findAllClosedProjects();
    }
    @GetMapping("/allProjects")
    public List<ProjectDto> findAllProjects() throws Exception {
        return projectService.findAllProjects();
    }

    @GetMapping("/findByAllSkills")
    public List<ProjectDto> findProjectsByMultipleSkills(@RequestBody ArrayList<Skill> skills) throws Exception {
        return projectService.findAllByRequiredSkillsContaining(skills);
    }
    @GetMapping("/findBySomeSkills")
    public List<ProjectDto> findProjectsBySomeSkills(@RequestBody ArrayList<Skill> skills) throws Exception {
        return projectService.findByAtLeastOneSkill(skills);
    }

    @PostMapping("/addUserToProject")
    public ProjectDto addUserToProject(@RequestParam Long id) throws Exception {
        return projectService.assignUserToProject(projectService.findProjectById(id).get());
    }
    @PostMapping("/addSkillstToProject")
    public void addSkillsToProject(@RequestParam Long projectId,@RequestParam List<String> skillIds) throws Exception {
        projectService.addSkillsToProject(projectId,skillIds);

    }
    @DeleteMapping("/retrieveUser")
    public void deleteFromProject(@RequestParam String username,@RequestParam Long projectId) throws Exception {
        projectService.retrieveUserFromProject(username,projectId);
    }
}