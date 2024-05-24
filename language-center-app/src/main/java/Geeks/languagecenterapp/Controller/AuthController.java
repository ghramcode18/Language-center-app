//package Geeks.languagecenterapp.Controller;
//
//import Geeks.languagecenterapp.CustomExceptions.CustomException;
//import Geeks.languagecenterapp.DTO.Request.LoginRequest;
//import Geeks.languagecenterapp.DTO.Request.RegisterRequest;
//import Geeks.languagecenterapp.Service.UserService;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.validation.Valid;
//import lombok.AllArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//
//@RestController
//@AllArgsConstructor
//@RequestMapping("api/auth")
//public class AuthController {
//
//    private final UserService userService;
//
//    @PostMapping("/register")
//    public ResponseEntity register(@Valid @RequestBody RegisterRequest registerRequest) {
//        try {
//            return new ResponseEntity<>(userService.registerUser(registerRequest), HttpStatus.CREATED);
//        } catch (CustomException ex) {
//            return ResponseEntity.status(ex.getStatusCode()).body(ex.getResponseBody());
//        }
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity login(@Valid @RequestBody LoginRequest loginRequest) {
//        try {
//            return new ResponseEntity<>(userService.loginUser(loginRequest), HttpStatus.OK);
//        } catch (CustomException ex) {
//            return ResponseEntity.status(ex.getStatusCode()).body(ex.getResponseBody());
//        }
//    }
//
//    @GetMapping("/logout")
//    public ResponseEntity logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
//        return new ResponseEntity(userService.logout(request, response, authentication), HttpStatus.OK);
//    }
//
//}
