package com.example.developercollaboration.Controller;

import com.example.developercollaboration.Model.User;
import com.example.developercollaboration.Repositories.UserRepository;
import com.example.developercollaboration.Service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final UserService userservice;

   @PostMapping(path = "/create")
    public ResponseEntity<User> createUser( @RequestBody User user) throws Exception {
       if("" == user.getUsername()){
           throw new Exception("Email must not be empty");
       }
       else if(userRepository.existsByUsername(user.getUsername())){
           throw new Exception("Email taken");
       } else{
           return    ResponseEntity.ok().body(userservice.saveUser(user));
       }

   }
   @GetMapping(path="/getUsers")
    public List<User> getUsers(){
       return userRepository.findAll();
   }
}