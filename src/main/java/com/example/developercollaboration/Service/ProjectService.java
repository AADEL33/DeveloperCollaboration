package com.example.developercollaboration.Service;
import com.example.developercollaboration.DTOs.ProjectDto;
import com.example.developercollaboration.Exceptions.ProjectExceptions.*;
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

    public Optional<ProjectDto> findById(Long id)  {
        if (!projectRepository.existsById(id)) throw new ProjectNotFoundException();
        return Optional.of(EntityToDtoMapper.ProjectToProjectDto(projectRepository.findProjectById(id).orElseThrow()));
    }
    public Optional<Project> findProjectById(Long id) {
        if (!projectRepository.existsById(id)) throw new ProjectNotFoundException();
        return projectRepository.findProjectById(id);
    }



    public void deleteProjectById(Long id) {
        if (!projectRepository.existsById(id)) throw new ProjectNotFoundException();
        else {
            projectRepository.deleteById(id);
        }
    }

    public List<ProjectDto> findAllOpenProjects()  {
        if (projectRepository.count() == 0) throw new  ProjectNotFoundException();
        else {
            return projectRepository.findAllByIsOpenTrue().stream().map(EntityToDtoMapper::ProjectToProjectDto).toList();
        }

    }

    public List<ProjectDto> findAllClosedProjects()  {
        if (projectRepository.count() == 0) throw new  ProjectNotFoundException();
        else {
            return projectRepository.findAllByIsOpenFalse().stream().map(EntityToDtoMapper::ProjectToProjectDto).toList();
        }

    }

    public List<ProjectDto> findAllProjects()  {
        if (projectRepository.count() == 0) throw new  ProjectNotFoundException();
        return projectRepository.findAll().stream().map(EntityToDtoMapper::ProjectToProjectDto).toList();
    }

    public List<ProjectDto> findAllByRequiredSkillsContaining(ArrayList<Skill> skills)  {
        List<Project> projects = projectRepository.findAll();
        if (skillService.getSkills().containsAll(skills)) {
            return projects.stream().filter(project -> project.getRequiredSkills().containsAll(skills)).map(EntityToDtoMapper::ProjectToProjectDto).toList();
        } else {
            throw new NoProjectRequiringAllSkillsException();
        }
    }

    public List<ProjectDto> findByAtLeastOneSkill(ArrayList<Skill> skills){
        List<Project> projects = projectRepository.findAll();
        if (skillService.getSkills().containsAll(skills)) {

            return projects.stream().filter(project -> project.getRequiredSkills().stream().anyMatch(skills::contains)).map(EntityToDtoMapper::ProjectToProjectDto).toList();

        } else {
            throw new NoProjectRequiringAtLeastOneSkillException();
        }

    }

    public ProjectDto assignUserToProject(Project project){
        User currentUser = userService.getCurrentUser();
        if (project.getContributors().contains(userRepository.findByUsername(currentUser.getUsername()))){
            throw new AlreadyMemberException();
        }

        else if (!project.getIsOpen()) {
            throw new MaxContributorsReachedException();
        }

        else {
            project.getContributors().add(currentUser);
            if (project.getContributors().size() == project.getMaxContributors()) project.setIsOpen(false);
            projectRepository.saveAndFlush(project);
            return EntityToDtoMapper.ProjectToProjectDto(project) ;
        }
    }

    public void addSkillsToProject(Long projectId, List<String> skillIds)  {
        ArrayList<Skill> skills = new ArrayList<>();
        skillIds.forEach(skillId -> skills.add(skillService.getSkillByName(skillId).orElseThrow()));
        Optional<Project> project = findProjectById(projectId);
        project.orElseThrow().getRequiredSkills().addAll(skills);
    }

    public void retrieveUserFromProject(String username, Long projectId) {
        User user = userRepository.findByUsername(username);
        Optional<Project> project = findProjectById(projectId);
        if (!project.orElseThrow().getContributors().contains(user)) {
            throw new UserNotMemberException();
        } else {
            project.get().getContributors().remove(user);
            project.get().setIsOpen(true);
        }
    }
}