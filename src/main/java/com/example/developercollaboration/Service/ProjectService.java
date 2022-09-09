package com.example.developercollaboration.Service;

import com.example.developercollaboration.DTOs.ProjectDto;
import com.example.developercollaboration.Model.Project;
import com.example.developercollaboration.Model.Skill;
import com.example.developercollaboration.Model.User;
import com.example.developercollaboration.Repositories.ProjectRepository;
import com.example.developercollaboration.Repositories.UserRepository;
import com.example.developercollaboration.mapper.EntityToDtoMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Data
@RequiredArgsConstructor
@Transactional
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final SkillService skillService;


    public void announceProject(Project project) {
        project.setCreator(userService.getCurrentUser());
        projectRepository.save(project);
    }

    public Optional<ProjectDto> findById(Long id) throws Exception {
        if (!projectRepository.existsById(id)) throw new Exception("No project Found with id" + id);
        return Optional.of(EntityToDtoMapper.ProjectToProjectDto(projectRepository.findProjectById(id).get()));
    }
    public Optional<Project> findProjectById(Long id) throws Exception {
        if (!projectRepository.existsById(id)) throw new Exception("No project Found with id" + id);
        return projectRepository.findProjectById(id);
    }



    public void deleteProjectById(Long id) throws Exception {
        if (!projectRepository.existsById(id)) throw new Exception("There is no project with this ID");
        else {
            projectRepository.deleteById(id);
        }
    }

    public List<ProjectDto> findAllOpenProjects() throws Exception {
        if (projectRepository.count() == 0) throw new Exception("No project found");
        else {
            return projectRepository.findAllByIsOpenTrue().stream().map(EntityToDtoMapper::ProjectToProjectDto).toList();
        }

    }

    public List<ProjectDto> findAllClosedProjects() throws Exception {
        if (projectRepository.count() == 0) throw new Exception("No project found");
        else {
            return projectRepository.findAllByIsOpenFalse().stream().map(EntityToDtoMapper::ProjectToProjectDto).toList();
        }

    }

    public List<ProjectDto> findAllProjects() throws Exception {
        if (projectRepository.count() == 0) throw new Exception("No project found");
        return projectRepository.findAll().stream().map(EntityToDtoMapper::ProjectToProjectDto).toList();
    }


    public List<ProjectDto> findAllByRequiredSkillsContaining(ArrayList<Skill> skills) throws Exception {
        List<Project> projects = projectRepository.findAll();
        if (skillService.getSkills().containsAll(skills)) {
            return projects.stream().filter(project -> project.getRequiredSkills().containsAll(skills)).map(EntityToDtoMapper::ProjectToProjectDto).toList();
        } else {
            throw new Exception("skills not all found");
        }
    }

    public List<ProjectDto> findByAtLeastOneSkill(ArrayList<Skill> skills) throws Exception {
        List<Project> projects = projectRepository.findAll();
        if (skillService.getSkills().containsAll(skills)) {

            return projects.stream().filter(project -> project.getRequiredSkills().stream().anyMatch(skills::contains)).map(EntityToDtoMapper::ProjectToProjectDto).toList();

        } else {
            throw new Exception("Some Skills are not found");
        }

    }

    public ProjectDto assignUserToProject(Project project) throws Exception {
        User currentUser = userService.getCurrentUser();
        if (!project.getIsOpen()) {
            throw new Exception("Sorry you can not join this project, the limit number of contributors is already reached");
        }

        if (project.getContributors().contains(userRepository.findByUsername(currentUser.getUsername())))
            throw new Exception("you are already member");
        else {
            project.getContributors().add(currentUser);
            if (project.getContributors().size() == project.getMaxContributors()) project.setIsOpen(false);
            projectRepository.saveAndFlush(project);
            return EntityToDtoMapper.ProjectToProjectDto(project) ;
        }
    }

    public void addSkillsToProject(Long projectId, List<String> skillIds) throws Exception {

        ArrayList<Skill> skills = new ArrayList<>();
        skillIds.forEach(skillId -> {
            try {
                skills.add(skillService.getSkillbyName(skillId).get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Optional<Project> project = findProjectById(projectId);
        project.get().getRequiredSkills().addAll(skills);
    }

    public void retrieveUserFromProject(String username, Long projectId) throws Exception {
        User user = userRepository.findByUsername(username);
        Optional<Project> project = findProjectById(projectId);
        if (!project.get().getContributors().contains(user)) {
            throw new Exception("The user" + user + " is not a member in " + project);
        } else {
            project.get().getContributors().remove(user);
            project.get().setIsOpen(true);
        }
    }
}