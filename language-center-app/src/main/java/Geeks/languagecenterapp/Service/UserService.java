package Geeks.languagecenterapp.Service;

import Geeks.languagecenterapp.CustomExceptions.CustomException;
import Geeks.languagecenterapp.DTO.Request.EnrollRequest;
import Geeks.languagecenterapp.DTO.Request.LoginRequest;
import Geeks.languagecenterapp.DTO.Request.RateRequest;
import Geeks.languagecenterapp.DTO.Request.RegisterRequest;
import Geeks.languagecenterapp.DTO.Response.Register_Login_Response;
import Geeks.languagecenterapp.DTO.Response.UserProfileResponse;
import Geeks.languagecenterapp.Model.*;
import Geeks.languagecenterapp.Model.Enum.ImageEnum;
import Geeks.languagecenterapp.Model.Enum.UserAccountEnum;
import Geeks.languagecenterapp.Repository.*;
import Geeks.languagecenterapp.Service.SecurityServices.EncryptionService;
import Geeks.languagecenterapp.Service.SecurityServices.JWTService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final ImageRepository imageRepository;

    @Autowired
    private final CourseRepository courseRepository;

    @Autowired
    private final EncryptionService encryptionService;

    @Autowired
    private final JWTService jwtService;

    @Autowired
    private final TokenService tokenService;

    @Autowired
    private final EnrollCourseRepository enrollCourseRepository;

    @Autowired
    private final FavoriteRepository favoriteRepository;


    public Register_Login_Response registerUser(RegisterRequest registerRequest) throws CustomException {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent() || userRepository.findByPhoneNumber(registerRequest.getPhone()).isPresent()) {
            throw new CustomException("Phone or Email Already Used", 409);
        } else {
            // Create New User
            UserEntity user = new UserEntity();
            user.setFirstName(registerRequest.getFirstName());
            user.setLastName(registerRequest.getLastName());
            user.setEmail(registerRequest.getEmail());
            user.setBio(registerRequest.getBio());
            user.setDob(registerRequest.getDob());
            user.setAccountType(UserAccountEnum.USER);
            user.setGender(registerRequest.getGender());
            user.setEducation(registerRequest.getEducation());
            user.setPhoneNumber(registerRequest.getPhone());
            user.setPassword(encryptionService.encryptPassword(registerRequest.getPassword()));
            String imageUrl = uploadFile(registerRequest.getImage());
            if (imageUrl != null) {
                List<ImageEntity> onlyProfileImage = new ArrayList<>();
                ImageEntity imageEntity = new ImageEntity();
                // initialize Image Object
                imageEntity.setImgUrl(imageUrl);
                imageEntity.setType(ImageEnum.PROFILE);
                imageEntity.setUser(user);
                onlyProfileImage.add(imageEntity);
                user.setImages(onlyProfileImage);
            }
            // Save User In DataBase
            UserEntity savedUser = userRepository.save(user);
            // Generate Token For User
            String generatedToken = jwtService.generateJWT(user);
            // Save Token In DataBase
            tokenService.saveUserToken(savedUser, generatedToken);
            // Initialize And return Response
            return initializeResponseObject(user, generatedToken);
        }
    }

    public UserProfileResponse userProfile(UserEntity user) {
        UserProfileResponse userProfileResponse = new UserProfileResponse();
        userProfileResponse.setUserInfo(user);
        userProfileResponse.setUserImages(imageRepository.findImageByUserId(user.getId()));
        return userProfileResponse;
    }

    public Register_Login_Response loginUser(LoginRequest loginRequest) throws CustomException {
        Optional<UserEntity> currentUser = userRepository.findByEmail(loginRequest.getEmail());
        if (currentUser.isPresent()) {
            UserEntity user = currentUser.get();
            if (encryptionService.verifyPassword(loginRequest.getPassword(), user.getPassword())) {
                String generatedToken = jwtService.generateJWT(user);
                tokenService.revokeOldUserTokens(user);
                tokenService.saveUserToken(user, generatedToken);
                // Initialize And return Response
                return initializeResponseObject(user, generatedToken);
            } else {
                throw new CustomException("Password Not Correct!", 400);
            }
        } else {
            throw new CustomException("Email Not Correct! Or Not Registered", 400);
        }
    }

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
        response.setImages(imageRepository.findImageByUserId(user.getId()));
        response.setToken(token);
        return response;
    }

    public Map<String, String> logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // Clear Session And Security Context Holder
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, authentication);
        // Extract User Id
        int uId = ((UserEntity) authentication.getPrincipal()).getId();
        // Revoke All User Tokens
        tokenService.revokeOldUserTokens(UserEntity.builder().id(uId).build());
        // Initialize And return Response
        Map<String, String> map = new HashMap<>();
        map.put("message", "Logout Successfully");
        return map;
    }

    // Upload Files
    private static final String UPLOAD_DIR = "DIRECTORY_PATH";

    private String uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }
        try {
            File dir = new File(UPLOAD_DIR);
            boolean dirIsCreated = false;
            // Create Directory If Not Exist
            if (!dir.exists()) {
                dirIsCreated = dir.mkdirs();
            }
            if (dir.exists() || dirIsCreated) {
                File uploadedFile = new File(dir, Objects.requireNonNull(file.getOriginalFilename()));
                file.transferTo(uploadedFile);
                return uploadedFile.getAbsolutePath();
            } else {
                return "Directory Not exist And Can not Created !";
            }
        } catch (IOException e) {
            return null;
        }
    }

    public List<UserEntity> getUsers(UserAccountEnum accountType) {
        return userRepository.findByAccountType(accountType);
    }

    public List<CourseEntity> getEnrolledCourses(UserEntity user) {
        List<EnrollCourseEntity> enrollments = enrollCourseRepository.findByUser(user);
        List<CourseEntity> courses = new ArrayList<>();
        for (EnrollCourseEntity enrollment : enrollments) {
            courses.add(enrollment.getCourse());
        }
        return courses;
    }

    // Get favorite courses of a user
    public List<CourseEntity> getFavoriteCourses(UserEntity user) {
        List<FavoriteEntity> favoriteCourses = favoriteRepository.findByUser(user);
        List<CourseEntity> courses = new ArrayList<>();
        for (FavoriteEntity favorite : favoriteCourses) {
            courses.add(favorite.getCourse());
        }
        return courses;
    }

    //Enroll in a course
    public ResponseEntity<Object> enroll(EnrollRequest RequestBody, int id) throws JsonProcessingException {

        Optional<CourseEntity> course = courseRepository.findById(id);
        Optional<UserEntity> student = userRepository.findById(RequestBody.getStd_id());
        // Check if the placement test exists
        if (!course.isPresent()) {
            // Create a response object with the success message
            String successMessage = "Course not found.";
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(successMessage);
            return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
        }

        // Check if the booking already exists for this student and course
        Optional<EnrollCourseEntity> existingBooking = enrollCourseRepository.findByUserIdAndCourseId(student.get().getId(), course.get().getId());
        if (existingBooking.isPresent()) {
            // Create a response object with the success message
            String successMessage = "enroll already exists.";
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(successMessage);
            return new ResponseEntity<>(jsonResponse, HttpStatus.CONFLICT);
        }

        // Create and save the new enroll
        EnrollCourseEntity enrollCourse = new EnrollCourseEntity();
        enrollCourse.setUser(student.get());
        enrollCourse.setCourse(course.get());
        enrollCourse.setEnrollDate(LocalDateTime.now());
        enrollCourseRepository.save(enrollCourse);

        // Create a response object with the success message
        String successMessage = "Enrolled added successfully.";
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(successMessage);
        return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
    }

    //rate course
    public ResponseEntity<Object> rate(RateRequest body, int id) throws JsonProcessingException {
        Optional<CourseEntity> course = courseRepository.findById(id);
        Optional<UserEntity> student = userRepository.findById(body.getStd_id());
        // Check if the placement test exists
        if (!course.isPresent()) {
            // Create a response object with the success message
            String successMessage = "Course not found.";
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(successMessage);
            return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
        }

        // Check if the booking already exists for this student and course
        Optional<EnrollCourseEntity> existingBooking = enrollCourseRepository.findByUserIdAndCourseId(student.get().getId(), course.get().getId());
        if (existingBooking.isPresent()) {
            existingBooking.get().setRate(body.getRate());
            enrollCourseRepository.save(existingBooking.get());
            // Create a response object with the success message
            String successMessage = "Rate added successfully....Thank you :)";
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(successMessage);
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        }

        // Create a response object with the success message
        String successMessage = "Something went wrong :(";
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(successMessage);
        return new ResponseEntity<>(jsonResponse, HttpStatus.BAD_REQUEST);
    }

}
