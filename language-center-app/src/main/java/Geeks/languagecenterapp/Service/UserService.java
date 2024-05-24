package Geeks.languagecenterapp.Service;

import Geeks.languagecenterapp.CustomExceptions.CustomException;
import Geeks.languagecenterapp.DTO.Request.LoginRequest;
import Geeks.languagecenterapp.DTO.Request.RegisterRequest;
import Geeks.languagecenterapp.DTO.Response.Register_Login_Response;
import Geeks.languagecenterapp.Model.Enum.UserAccountEnum;
import Geeks.languagecenterapp.Model.PostEntity;
import Geeks.languagecenterapp.Model.UserEntity;
import Geeks.languagecenterapp.Repository.PostRepository;
import Geeks.languagecenterapp.Repository.UserRepository;
//import Geeks.languagecenterapp.Service.SecurityServices.EncryptionService;
import Geeks.languagecenterapp.Service.SecurityServices.JWTService;

import lombok.AllArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PostRepository postRepository;

//    private final EncryptionService encryptionService;

    private final JWTService jwtService;

    private final TokenService tokenService;


//    public Register_Login_Response registerUser(RegisterRequest registerRequest) throws CustomException {
//        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
//            throw new CustomException("Email Already Used", 409);
//        } else {
//            // Create New User
//            UserEntity user = new UserEntity();
//            user.setFirstName(registerRequest.getFirstName());
//            user.setLastName(registerRequest.getLastName());
//            user.setEmail(registerRequest.getEmail());
//            user.setBio(registerRequest.getBio());
//            user.setDob(registerRequest.getDob());
//            user.setAccountType(UserAccountEnum.USER);
//            user.setGender(registerRequest.getGender());
//            user.setEducation(registerRequest.getEducation());
//            user.setPhoneNumber(registerRequest.getPhone());
//            user.setPassword(encryptionService.encryptPassword(registerRequest.getPassword()));
//            // Save User In DataBase
//            UserEntity savedUser = userRepository.save(user);
//            // Generate Token For User
//            String generatedToken = jwtService.generateJWT(user);
//            // Save Token In DataBase
//            tokenService.saveUserToken(savedUser, generatedToken);
//            // Initialize And return Response
//            return initializeResponseObject(user, generatedToken);
//        }
//    }

//    public Register_Login_Response loginUser(LoginRequest loginRequest) throws CustomException {
//        Optional<UserEntity> currentUser = userRepository.findByEmail(loginRequest.getEmail());
//        if (currentUser.isPresent()) {
//            UserEntity user = currentUser.get();
//            if (encryptionService.verifyPassword(loginRequest.getPassword(), user.getPassword())) {
//                String generatedToken = jwtService.generateJWT(user);
//                tokenService.revokeOldUserTokens(user);
//                tokenService.saveUserToken(user, generatedToken);
//                // Initialize And return Response
//                return initializeResponseObject(user, generatedToken);
//            } else {
//                throw new CustomException("Password Not Correct!", 400);
//            }
//        } else {
//            throw new CustomException("Email Not Correct! Or Not Registered", 400);
//        }
//    }

    private Register_Login_Response initializeResponseObject(UserEntity user, String token) {
        Register_Login_Response response = new Register_Login_Response();
        response.setMessage("Successfully Operation");
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setBio(user.getBio());
        response.setGender(user.getGender());
        response.setDob(user.getDob());
        response.setEducation(user.getEducation());
        response.setPhone(user.getPhoneNumber());
        response.setToken(token);
        return response;
    }

//    public Map<String, String> logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
//        // Clear Session And Security Context Holder
//        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
//        securityContextLogoutHandler.logout(request, response, authentication);
//        // Extract User Id
//        int uId = ((UserEntity) authentication.getPrincipal()).getId();
//        // Revoke All User Tokens
//        tokenService.revokeOldUserTokens(UserEntity.builder().id(uId).build());
//        // Initialize And return Response
//        Map<String, String> map = new HashMap<>();
//        map.put("message", "Logout Successfully");
//        return map;
//    }


    public List <PostEntity> getPosts(String type) {
        return postRepository.findByType(type);
    }

    public List <UserEntity> getUsers(UserAccountEnum accountType) {
        return userRepository.findByAccountType(accountType);
    }
}
