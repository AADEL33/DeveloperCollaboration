package com.example.developercollaboration.Service;
import com.example.developercollaboration.DTOs.CommentDto;
import com.example.developercollaboration.Exceptions.CommentExceptions.CantDeleteCommentException;
import com.example.developercollaboration.Exceptions.CommentExceptions.CommentNotFoundException;
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


    public void addCommentToProject(String commentary, Long ProjectId){

        Optional<Project> project=projectRepository.findById(ProjectId);
        Comment comment=new Comment();
        comment.setProject(project.get());
        comment.setUser(userService.getCurrentUser());
        comment.setComment(commentary);
        commentRepository.save(comment);
        project.get().getComments().add(comment);
    }

    public void deleteCommentFromProject(Long CommentId,Long ProjectId) throws Exception {
        Optional<Project> project=projectRepository.findById(ProjectId);
        Optional<Comment> comment=commentRepository.findById(CommentId);
        if(!project.get().getComments().contains(comment.get()) || comment.get().getUser()!=userService.getCurrentUser()) {
            throw new CantDeleteCommentException();
        }
        project.get().getComments().remove(comment.get());
        commentRepository.deleteById(CommentId);
    }

    public List<CommentDto> getMyCommentsOnProjects(User user)  {
        if(commentRepository.count()==0){
            throw new CommentNotFoundException();
        }
        return  commentRepository.findAllByUser(user);
    }


}