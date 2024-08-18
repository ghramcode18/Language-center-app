package Geeks.languagecenterapp.Controller;

import Geeks.languagecenterapp.CustomExceptions.CustomException;
import Geeks.languagecenterapp.DTO.Request.LoginRequest;
import Geeks.languagecenterapp.DTO.Request.PasswordResetRequest;
import Geeks.languagecenterapp.DTO.Request.PasswordResetTokenRequest;
import Geeks.languagecenterapp.DTO.Request.RegisterRequest;
import Geeks.languagecenterapp.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@AllArgsConstructor
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @ModelAttribute RegisterRequest registerRequest) {
        try {
            return new ResponseEntity<>(userService.registerUser(registerRequest), HttpStatus.CREATED);
        } catch (CustomException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getResponseBody());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @ModelAttribute LoginRequest loginRequest) {
        try {
            return new ResponseEntity<>(userService.loginUser(loginRequest), HttpStatus.OK);
        } catch (CustomException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getResponseBody());
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        return new ResponseEntity<>(userService.logout(request, response, authentication), HttpStatus.OK);
    }


    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> initiatePasswordReset( @ModelAttribute PasswordResetRequest passwordResetRequest) {
        Map<String, String> response = new HashMap<>();
        try {
        userService.initiatePasswordReset(passwordResetRequest);
        response.put("message", "Password reset code sent to email");
        return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (CustomException ex) {
            response.put("message", "please enter valid email");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }}

    @PostMapping("/check-code")
    public ResponseEntity<Map<String, String>> resetPassword( @ModelAttribute PasswordResetTokenRequest passwordResetTokenRequest) {
        userService.CheckCode(passwordResetTokenRequest);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Code has been check successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/change-password")
    public ResponseEntity<Map<String, String>> changePassword( @ModelAttribute PasswordResetTokenRequest passwordResetTokenRequest) {
        userService.changePassword(passwordResetTokenRequest);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Password has been changed successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/Verify-account")
    public ResponseEntity<Map<String, String>> VerifyAccount(  @ModelAttribute PasswordResetRequest passwordResetRequest) {
        Map<String, String> response = new HashMap<>();
        try {
            userService.VerifyAccount(passwordResetRequest);
            response.put("message", "Verify code sent to email");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CustomException ex) {
            response.put("message", "please enter valid email");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }}


    @PostMapping("/activated-account")
    public ResponseEntity<Map<String, String>> ActivatedAccount( @ModelAttribute PasswordResetTokenRequest passwordResetTokenRequest) {
        userService.ActivatedAccount(passwordResetTokenRequest);
        Map<String, String> response = new HashMap<>();
        response.put("message", "The account has been activate successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}