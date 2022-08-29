package com.example.developercollaboration.Service;

import com.example.developercollaboration.Model.User;
import com.example.developercollaboration.Repositories.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@Data
public class UserService implements UserDetailsService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public User saveUser(User user)throws Exception{
        if("" == user.getUsername()){
            throw new Exception("Email must not be empty");
        }
        else if(userRepo.existsByUsername(user.getUsername())){
            throw new Exception("Email taken");
        } else{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return   userRepo.save(user);
        }

    }
    public void deleteUser(String username)throws Exception{
        if(!userRepo.existsByUsername(username)){
            throw new Exception("user not found");

        }
        userRepo.deleteByUsername(username);
    }

    public User UpdateUsername(String username){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user =userRepo.findByUsername((String) authentication.getPrincipal());
        user.setUsername(username);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);

        if(user == null) {
            log.error("User not found in database");
            throw new UsernameNotFoundException("User not found in database");
        } else {
            log.info("User found in the database {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(
                role -> {
                    authorities.add(new SimpleGrantedAuthority(role.toString()));
                }
        );
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }





}