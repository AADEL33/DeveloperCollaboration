package com.example.developercollaboration.Controller;

import com.example.developercollaboration.DTOs.CommentDto;
import com.example.developercollaboration.Service.CommentService;
import com.example.developercollaboration.Service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Data
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;
    @GetMapping("/myComments")
    public List<CommentDto> MyComments() throws Exception {
       return  commentService.getMyCommentsOnProjects(userService.getCurrentUser());
    }

    @PostMapping("/addComment")
    public void addCommentToProject(@RequestBody String comment, @RequestParam Long projectId) throws Exception {
        commentService.addCommentToProject(comment,projectId);
    }
    @DeleteMapping("/deleteComment")
    public void deleteComment(@RequestParam Long commentId,@RequestParam Long projectId) throws Exception {
        commentService.deleteCommentFromProject(commentId,projectId);
    }


}