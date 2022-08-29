package com.example.developercollaboration.Controller;

import com.example.developercollaboration.Model.User;
import com.example.developercollaboration.Repositories.UserRepository;
import com.example.developercollaboration.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Transactional
public class UserController {
    private final UserRepository userRepository;
    private final UserService userservice;
    @CrossOrigin(origins = "http://localhost:3000")
   @PostMapping(path = "/create")
    public User createUser( @RequestBody User user) throws Exception {
      return userservice.saveUser(user);

   }
   @DeleteMapping(path="/deleteUser/{username}")
   //@ResponseBody
    public void deleteUser(@PathVariable(value = "username") String username)throws Exception{
       userservice.deleteUser(username);
   }
   @PostMapping (path="/updateUsername")
    public User updateusername(@RequestParam String username){
      return userservice.UpdateUsername(username);
   }
}