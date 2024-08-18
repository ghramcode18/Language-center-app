package Geeks.languagecenterapp.Service;

import Geeks.languagecenterapp.CustomExceptions.CustomException;
import Geeks.languagecenterapp.DTO.Request.*;
import Geeks.languagecenterapp.DTO.Response.CourseDayResponse;
import Geeks.languagecenterapp.DTO.Response.CourseResponse;
import Geeks.languagecenterapp.DTO.Response.Register_Login_Response;
import Geeks.languagecenterapp.DTO.Response.UserProfileResponse;
import Geeks.languagecenterapp.Model.*;
import Geeks.languagecenterapp.Model.Enum.ImageEnum;
import Geeks.languagecenterapp.Model.Enum.UserAccountEnum;
import Geeks.languagecenterapp.Repository.*;
import Geeks.languagecenterapp.Service.SecurityServices.EncryptionService;
import Geeks.languagecenterapp.Service.SecurityServices.JWTService;
import Geeks.languagecenterapp.Tools.FilesManagement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private  ImageRepository imageRepository;

    @Autowired
    private  CourseRepository courseRepository;

    @Autowired
    private  EncryptionService encryptionService;

    @Autowired
    private  JWTService jwtService;

    @Autowired
    private  TokenService tokenService;

    @Autowired
    private  EnrollCourseRepository enrollCourseRepository;

    @Autowired
    private  FavoriteRepository favoriteRepository;

    @Autowired
    private  UserRateRepository userRateRepository;

    @Autowired
    private  CourseImageRepository courseImageRepository;

    @Autowired
    private EmailService emailService;

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
            switch (registerRequest.getUserType()) {
                case USER:
                    user.setAccountType(UserAccountEnum.USER);
                    break;
                case TEACHER:
                    user.setAccountType(UserAccountEnum.TEACHER);
                    break;
                case ADMIN:
                    user.setAccountType(UserAccountEnum.ADMIN);
                    break;
                case SECRETARY:
                    user.setAccountType(UserAccountEnum.SECRETARY);
                    break;
                case GUEST:
                    user.setAccountType(UserAccountEnum.GUEST);
                    break;
                default:
                    throw new CustomException("Wrong User Type", 400);
            }
            user.setGender(registerRequest.getGender());
            user.setEducation(registerRequest.getEducation());
            user.setPhoneNumber(registerRequest.getPhone());
            user.setPassword(encryptionService.encryptPassword(registerRequest.getPassword()));
            String imageUrl = FilesManagement.uploadSingleFile(registerRequest.getImage());
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
        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setBio(user.getBio());
        response.setGender(user.getGender());
        response.setDob(user.getDob());
        response.setRole(user.getAccountType());
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

    public List<UserEntity> getUsers(UserAccountEnum accountType) {
        return userRepository.findByAccountType(accountType);
    }

    public List<CourseResponse> getEnrolledCourses(UserEntity user) {
        List<EnrollCourseEntity> enrollments = enrollCourseRepository.findByUser(user);
        List<CourseEntity> courses = new ArrayList<>();
        for (EnrollCourseEntity enrollment : enrollments) {
            courses.add(enrollment.getCourse());
        }
        return courses.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    // Convert CourseEntity to CourseDTO
    private CourseResponse convertToDTO(CourseEntity course) {
        CourseResponse dto = new CourseResponse();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        double newPrice=0;
        double price=course.getPrice();
        int discount=course.getDiscount();
        newPrice=price-((price*discount)/100);
        dto.setPrice(newPrice);
        dto.setNumOfHours(course.getNumOfHours());
        dto.setNumOfSessions(course.getNumOfSessions());
        dto.setNumOfRoom(course.getNumOfRoom());
        dto.setStartDate(course.getStartDate());
        dto.setProgress(course.getProgress());
        dto.setLevel(course.getLevel());
        dto.setDiscount(course.getDiscount());
//        dto.setRating(courseRepository.findAverageRatingByCourseId(course.getId()));
        dto.setImage(courseImageRepository.findByCourseId(course.getId()));
        List<CourseDayResponse> courseDayDTOs = course.getCourseDayList().stream().map(this::convertToCourseDayDTO).collect(Collectors.toList());
        dto.setCourseDayList(courseDayDTOs);
        return dto;
    }

    // Convert CourseDayEntity to CourseDayDTO
    private CourseDayResponse convertToCourseDayDTO(CourseDayEntity courseDay) {
        CourseDayResponse dto = new CourseDayResponse();
        dto.setDay(courseDay.getDay().getDay());
        dto.setCourseTime(courseDay.isCourseTime() ? "Morning" : "Evening");
        return dto;
    }
    // Get favorite courses of a user
    public List<CourseResponse> getFavoriteCourses(UserEntity user) {
        List<FavoriteEntity> favoriteCourses = favoriteRepository.findByUser(user);
        List<CourseEntity> courses = new ArrayList<>();
        for (FavoriteEntity favorite : favoriteCourses) {
            courses.add(favorite.getCourse());
        }
        return courses.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    //Enroll in a course
    public ResponseEntity<Object> enroll(EnrollRequest RequestBody, int id) {
        Map<String, String> response = new HashMap<>();

        Optional<CourseEntity> course = courseRepository.findById(id);
        Optional<UserEntity> student = userRepository.findById(RequestBody.getStd_id());
        // Check if the placement test exists
        if (!course.isPresent()) {
            // Create a response object with the success message
            response.put("message", "Course Not Found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        // Check if the booking already exists for this student and course
        Optional<EnrollCourseEntity> existingBooking = enrollCourseRepository.findByUserIdAndCourseId(student.get().getId(), course.get().getId());
        if (existingBooking.isPresent()) {
            // Create a response object with the success message
            response.put("message", "Enroll Course Already Exists.");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        // Create and save the new enroll
        EnrollCourseEntity enrollCourse = new EnrollCourseEntity();
        enrollCourse.setUser(student.get());
        enrollCourse.setCourse(course.get());
        enrollCourse.setEnrollDate(LocalDateTime.now());
        enrollCourseRepository.save(enrollCourse);

        // Create a response object with the success message
        response.put("message", "Enroll Successfully.");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //rate course
    public ResponseEntity<Object> rateCourse(RateRequest body, int id) {
        Map<String, String> response = new HashMap<>();
        Optional<CourseEntity> course = courseRepository.findById(id);
        Optional<UserEntity> student = userRepository.findById(body.getStd_id());
        // Check if the placement test exists
        if (!course.isPresent()) {
            // Create a response object with the success message
            response.put("message", "Course Not Found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        // Check if the booking already exists for this student and course
        Optional<EnrollCourseEntity> existingBooking = enrollCourseRepository.findByUserIdAndCourseId(student.get().getId(), course.get().getId());
        if (existingBooking.isPresent() && student.get().getAccountType() == UserAccountEnum.USER) {
            existingBooking.get().setRate(body.getRate());
            enrollCourseRepository.save(existingBooking.get());
            // Create a response object with the success message
            response.put("message", "Rate added successfully :)");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        // Create a response object with the success message
        response.put("message", "Something went wrong.");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //rate teacher
    public ResponseEntity<Object> rateTeacher(RateRequest body, int id) throws JsonProcessingException {
        Map<String, String> response = new HashMap<>();
        Optional<UserEntity> teacher = userRepository.findById(id);
        if (!teacher.isPresent()) {
            // Create a response object with the success message
            response.put("message", "Teacher Not Found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        Optional<UserRateEntity> teacherRate = userRateRepository.findById(teacher.get().getId());
        if (teacherRate.isPresent() && teacher.get().getAccountType() == UserAccountEnum.TEACHER) {
            teacherRate.get().setUser(teacher.get());
            teacherRate.get().setDate(LocalDateTime.now());
            teacherRate.get().setCountRate(teacherRate.get().getCountRate() + 1);
            teacherRate.get().setCountSum(teacherRate.get().getCountSum() + body.getRate());
            userRateRepository.save(teacherRate.get());

            // Create a response object with the success message
            response.put("message", "Rate added successfully :)");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {//first Rate
            if (teacher.get().getAccountType() == UserAccountEnum.TEACHER) {
                UserRateEntity newRate = new UserRateEntity();
                newRate.setUser(teacher.get());
                newRate.setDate(LocalDateTime.now());
                newRate.setCountRate(1);
                newRate.setCountSum(body.getRate());
                userRateRepository.save(newRate);

                // Create a response object with the success message
                response.put("message", "Rate added successfully :)");
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
        }
        // Create a response object with the success message
        response.put("message", "Something went wrong.");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Object> getTeacherRate(int id) throws JsonProcessingException {
        Map<String, String> response = new HashMap<>();
        Optional<UserEntity> teacher = userRepository.findById(id);
        Optional<UserRateEntity> teacherRate = userRateRepository.findByUser(teacher.get());
        if (teacherRate.isPresent() && teacher.get().getAccountType() == UserAccountEnum.TEACHER) {

            // Calculate average rate
            float averageRate = teacherRate.get().getCountSum() / (float) teacherRate.get().getCountRate();

            // Build response
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(averageRate);
            response.put("Rate", jsonResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            // Create a response object with the success message
            response.put("message", "Teacher Not Found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> uploadCertificates(List<MultipartFile> files) {
        Map<String, String> response = new HashMap<>();

        //Check User Role
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) authentication.getPrincipal();
        Enum<UserAccountEnum> role = user.getAccountType();

        if (role.equals(UserAccountEnum.TEACHER) || role.equals(UserAccountEnum.ADMIN)) {
            List<String> resultPaths = FilesManagement.uploadMultipleFile(files);
            if (resultPaths == null) {
                response.put("message", "No Files Selected");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            } else if (resultPaths.isEmpty()) {
                response.put("message", "Error When Uploading Files.. Please Try Again Later");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                for (String item : resultPaths) {
                    ImageEntity imageEntity = new ImageEntity();
                    imageEntity.setUser(user);
                    imageEntity.setType(ImageEnum.CERTIFICATE);
                    imageEntity.setImgUrl(item);
                    imageRepository.save(imageEntity);
                }
                return new ResponseEntity<>(resultPaths, HttpStatus.OK);
            }

        } else {
            response.put("message", "Only Teachers And Admins Can Upload Certificates");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }
//    public void resetPassword(String token, String newPassword) throws CustomException {
//        UserEntity user = userRepository.findByPasswordResetToken(token);
//        if (user == null) {
//            throw new CustomException("Invalid password reset token", 400);
//        }
//        //user.setPassword(passwordEncoder.encode(newPassword));
//        user.setPasswordResetToken(null); // Clear the token after resetting the password
//        userRepository.save(user);
//    }
//



    private final Map<String, String> resetCodeStorage = new HashMap<>();

    private String code =generateRandomCode();

    public void initiatePasswordReset(PasswordResetRequest passwordResetRequest) {
        if(isValidGmail(passwordResetRequest.getEmail()))
        {emailService.sendPasswordResetEmail(passwordResetRequest.getEmail(), "Milestone Password Reset Code",  code);}
    }

    public void VerifyAccount(PasswordResetRequest passwordResetRequest) {
        if(isValidGmail(passwordResetRequest.getEmail()))
        {emailService.sendPasswordResetEmail(passwordResetRequest.getEmail(), "Milestone Verify Account Code",  code);}
    }

    public void ActivatedAccount(PasswordResetTokenRequest passwordResetTokenRequest) {
        UserEntity user = userRepository.findByEmail(passwordResetTokenRequest.getEmail()).get();
        user.setVerified(true);
        userRepository.save(user);
    }



    public void CheckCode(PasswordResetTokenRequest passwordResetTokenRequest) {
        String storedCode = code;
        if (storedCode == null || !storedCode.equals(passwordResetTokenRequest.getCode())) {
            throw new CustomException("Invalid or expired code", 400);
        }
    }

    public boolean isValidGmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
        return email.matches(regex);
    }


    public void changePassword(PasswordResetTokenRequest passwordResetTokenRequest) {
        UserEntity user = userRepository.findByEmail(passwordResetTokenRequest.getEmail()).get();
        user.setPassword(passwordResetTokenRequest.getNewPassword());
        userRepository.save(user);
    }

    public String generateRandomCode() {
        SecureRandom random = new SecureRandom();
         code =String.valueOf(10000 + random.nextInt(90000)) ;
        return code;
    }
}
