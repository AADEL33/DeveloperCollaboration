package com.example.developercollaboration.Service;

import com.example.developercollaboration.Model.Comment;
import com.example.developercollaboration.Model.Project;
import com.example.developercollaboration.Model.User;
import com.example.developercollaboration.Repositories.CommentRepository;
import com.example.developercollaboration.Repositories.ProjectRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Data
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final  ProjectRepository projectRepository;
    private final  ProjectService projectService;
    private final UserService userService;


    public void addCommentToProject(String commentary, Long ProjectId) throws Exception {

        Project project=projectService.findProjectById(ProjectId);
        Comment comment=new Comment();
        comment.setProject(project);
        comment.setUser(userService.getCurrentUser());
        comment.setComment(commentary);
        commentRepository.save(comment);
        project.getComments().add(comment);
    }

    public void deleteCommentFromProject(Long CommentId,Long ProjectId) throws Exception {
        if(!projectRepository.existsById(ProjectId)||!commentRepository.existsById(CommentId)){
            throw new Exception("Project or comment not found");
        }
        Project project=projectService.findProjectById(ProjectId);
        Optional<Comment> comment=commentRepository.findById(CommentId);
        if(!project.getComments().contains(comment)) {
            throw new Exception("this project does not contains this comment");
        }
        project.getComments().remove(comment);
    }

    public List<Comment> getMyCommentsOnProjects(User user) throws Exception {
        if(commentRepository.count()==0){
            throw new Exception("No comment found");
        }
        return commentRepository.findCommentsByUser(user);
    }


}