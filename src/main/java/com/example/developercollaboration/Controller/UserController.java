package com.example.developercollaboration.Controller;

import com.example.developercollaboration.Model.User;
import com.example.developercollaboration.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.nio.file.attribute.UserPrincipalNotFoundException;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserController {
    private final UserService userservice;

    @PostMapping(path = "/create")
    public User createUser(@RequestBody User user) throws Exception {
        return userservice.saveUser(user);
    }

    @DeleteMapping(path = "/deleteUser/{username}")
    //@ResponseBody
    public void deleteUser(@PathVariable(value = "username") String username) throws Exception {
        userservice.deleteUser(username);
    }

    @PostMapping(path = "/updateUsername")
    public User updateusername(@RequestParam String username) {
        return userservice.UpdateUsername(username);
    }

    @PostMapping("/forgot_password")
    @ResponseStatus(HttpStatus.OK)
    public void processForgotPassword(HttpServletRequest request) throws UserPrincipalNotFoundException, MessagingException, UnsupportedEncodingException {
        userservice.processForgotPassword(request);
    }

    @PostMapping("/reset_password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void processResetPassword(HttpServletRequest request) throws IllegalAccessException {
        userservice.processResetPassword(request);
        log.info("successfully updated the password");
    }


}