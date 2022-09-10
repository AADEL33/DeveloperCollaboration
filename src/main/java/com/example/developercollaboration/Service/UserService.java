package com.example.developercollaboration.Service;
import com.example.developercollaboration.DTOs.UserDto;
import com.example.developercollaboration.Exceptions.UserExceptions.AlraedyHaveAccountException;
import com.example.developercollaboration.Exceptions.UserExceptions.EmailNotValidException;
import com.example.developercollaboration.Exceptions.UserExceptions.ResetPasswordFailedException;
import com.example.developercollaboration.Exceptions.UserExceptions.UserNotFoundException;
import com.example.developercollaboration.Model.User;
import com.example.developercollaboration.Repositories.UserRepository;
import com.example.developercollaboration.mapper.EntityToDtoMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@Data


public class UserService implements UserDetailsService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    private final EmailValidator emailValidator;

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
                role -> authorities.add(new SimpleGrantedAuthority(role.toString()))
        );
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    public UserDto saveUser(User user)throws Exception{
        if(!emailValidator.isValidEmail(user.getUsername())){
            throw new EmailNotValidException();
        }
        else if(userRepo.existsByUsername(user.getUsername())){
            throw new AlraedyHaveAccountException();
        } else{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return EntityToDtoMapper.UserToUserDto(userRepo.save(user)) ;
        }

    }
    public void deleteUser(String username){
        if(!userRepo.existsByUsername(username)){
            throw new UserNotFoundException();

        }
        userRepo.deleteByUsername(username);
    }

    public UserDto UpdateUsername(String username){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user =userRepo.findByUsername((String) authentication.getPrincipal());
        user.setUsername(username);
        return EntityToDtoMapper.UserToUserDto(user) ;
    }

    public void updateResetPasswordToken(String token, String email) {
        User user = userRepo.findByUsername(email);
        if(user!=null){
            user.setResetPasswordToken(token);
            userRepo.save(user);
        }else{
            throw new UserNotFoundException();
        }
    }

    public  void updatePassword(User user, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        user.setResetPasswordToken(null);
        userRepo.save(user);
    }
    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message =
                mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("abdelhalim_aadel@um5.ac.ma", "Developer Collaboration service");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }

    public User getCurrentUser(){
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepo.findByUsername(username);
    }
    public void processResetPassword(HttpServletRequest request) throws ResetPasswordFailedException {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        User user = getCurrentUser();
        if (!Objects.equals(user.getResetPasswordToken(), token)){
            throw new ResetPasswordFailedException();
        }
        updatePassword(user,password);
    }
    public void processForgotPassword(HttpServletRequest request) throws  MessagingException, UnsupportedEncodingException {
        String email = request.getParameter("email");
        String token = RandomString.make(30);
        String resetPasswordLink = ServletUriComponentsBuilder.fromRequestUri(request).replacePath(null).build() + "/reset_password?token=" + token;
        updateResetPasswordToken(token, email);
        sendEmail(email, resetPasswordLink);
    }
}