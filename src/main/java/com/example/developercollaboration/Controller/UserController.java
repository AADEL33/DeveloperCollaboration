package com.example.developercollaboration.Controller;
import com.example.developercollaboration.Model.User;
import com.example.developercollaboration.Service.UserService;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.nio.file.attribute.UserPrincipalNotFoundException;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Transactional
public class UserController {
    private final UserService userservice;

    private final JavaMailSender mailSender;
    //@CrossOrigin(origins = "http://localhost:3000")
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
   @PostMapping("/forgot_password")
    public String processForgotPassword( HttpServletRequest request, Model model) {
       // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = request.getParameter("email");
        String token = RandomString.make(30);

        try {
            userservice.updateResetPasswordToken(token, email);
            String resetPasswordLink = ServletUriComponentsBuilder.fromRequestUri(request).replacePath(null).build() + "/reset_password?token=" + token;
            sendEmail(email, resetPasswordLink);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");

        } catch (UserPrincipalNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "forgot_password_form";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        User user = userservice.getByResetPasswordToken(token);
        model.addAttribute("title", "Reset your password");

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        } else {
            userservice.updatePassword(user, password);

            model.addAttribute("message", "You have successfully changed your password.");
        }

        return "message";
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


}