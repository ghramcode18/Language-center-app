package Geeks.languagecenterapp.Service;

import Geeks.languagecenterapp.DTO.Request.*;
import Geeks.languagecenterapp.DTO.Response.CourseDayResponse;
import Geeks.languagecenterapp.DTO.Response.CourseResponse;
import Geeks.languagecenterapp.Model.*;
import Geeks.languagecenterapp.Model.Enum.PostImageEnum;
import Geeks.languagecenterapp.Model.Enum.UserAccountEnum;
import Geeks.languagecenterapp.Repository.*;
import Geeks.languagecenterapp.Tools.FilesManagement;
import Geeks.languagecenterapp.Tools.HandleCurrentUserSession;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private EnrollCourseRepository enrollCourseRepository;
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private DayRepository dayRepository;
    @Autowired
    private CourseDayRepository courseDayRepository;
    @Autowired
    private CourseImageRepository courseImageRepository;
    @Autowired
    private MarkRepository markRepository;
    @Autowired
    private HomeWorkRepository homeWorkRepository;


    //Add Course by admin and return ok , return bad request response otherwise
    public ResponseEntity<?> add(CourseRequest courseRequest) throws JsonProcessingException {
        Map<String, String> response = new HashMap<>();
        try {
            CourseEntity course = new CourseEntity();
            Optional<UserEntity> teacher = userRepository.findById(courseRequest.getTeacher_id());
            Optional<ServiceEntity> service = serviceRepository.findById(courseRequest.getService_id());
            if (teacher.isPresent() && service.isPresent()) {//&& teacher.get().getAccountType() == UserAccountEnum.TEACHER) {
                course.setUser(teacher.get());
                course.setService(service.get());
                course.setTitle(courseRequest.getTitle());
                course.setDescription(courseRequest.getDescription());
                course.setPrice(courseRequest.getPrice());
                course.setNumOfHours(courseRequest.getNumOfHours());
                course.setNumOfSessions(courseRequest.getNumOfSessions());
                course.setNumOfRoom(courseRequest.getNumOfRoom());
                // Manually parse the startDate from String to LocalDateTime
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime parsedStartDate = LocalDateTime.parse(courseRequest.getStartDate(), formatter);
                course.setStartDate(parsedStartDate);
                course.setLevel(courseRequest.getLevel());
                courseRepository.save(course);
                String imageUrl = FilesManagement.uploadSingleFile(courseRequest.getImage());
                if (imageUrl != null) {
                    CourseImageEntity imageEntity = new CourseImageEntity();
                    // initialize Image Object
                    imageEntity.setImgUrl(imageUrl);
                    imageEntity.setImageType(PostImageEnum.Course_Img);
                    imageEntity.setCourse(course);
                    courseImageRepository.save(imageEntity);
                }
                // Create a response object with the success message
                response.put("message", "Course added successfully.");
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else if (!teacher.isPresent()) {
                // Create a response object with the success message
                response.put("message", "Teacher not found.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            } else if (!service.isPresent()) {
                // Create a response object with the success message
                response.put("message", "Service not found.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            } else {
                // Create a response object with the success message
                response.put("message", "Something went wrong.");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            // Create a response object with the error message
            response.put("message", "Some Error Occurred.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Search for Course by id ...if found -> update info ...else return not found response
    public ResponseEntity<Object> update(CourseRequest courseRequest, int id) throws JsonProcessingException {
        Map<String, String> response = new HashMap<>();

        Optional<CourseEntity> course = courseRepository.findById(id);
        if (course.isPresent()) {
            try {
                Optional<UserEntity> user = userRepository.findById(courseRequest.getTeacher_id());
                Optional<ServiceEntity> service = serviceRepository.findById(courseRequest.getService_id());
                if (user.isPresent() && service.isPresent()) {
                    course.get().setUser(user.get());
                    course.get().setService(service.get());
                    course.get().setTitle(courseRequest.getTitle());
                    course.get().setDescription(courseRequest.getDescription());
                    course.get().setPrice(courseRequest.getPrice());
                    course.get().setNumOfHours(courseRequest.getNumOfHours());
                    course.get().setNumOfSessions(courseRequest.getNumOfSessions());
                    course.get().setNumOfRoom(courseRequest.getNumOfRoom());
                    // Manually parse the startDate from String to LocalDateTime
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime parsedStartDate = LocalDateTime.parse(courseRequest.getStartDate(), formatter);
                    course.get().setStartDate(parsedStartDate);
                    course.get().setLevel(courseRequest.getLevel());
                    courseRepository.save(course.get());
                    String imageUrl = FilesManagement.uploadSingleFile(courseRequest.getImage());
                    if (imageUrl != null) {
                        CourseImageEntity imageEntity = new CourseImageEntity();
                        // initialize Image Object
                        imageEntity.setImgUrl(imageUrl);
                        imageEntity.setImageType(PostImageEnum.Course_Img);
                        imageEntity.setCourse(course.get());
                        courseImageRepository.save(imageEntity);
                    }
                    // Create a response object with the success message
                    response.put("message", "Course updated successfully.");
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    // Create a response object with the success message
                    response.put("message", "Something went wrong.");
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }

            } catch (Exception e) {
                // Create a response object with the success message
                response.put("message", "Some Error Occurred.");
                response.put("error", e.getMessage());
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            // Create a response object with the success message
            response.put("message", "Course Not Found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    //Search for Course by id ...if found -> delete info ...else return not found response
    public ResponseEntity<Object> delete(int id) throws JsonProcessingException {
        Map<String, String> response = new HashMap<>();

        Optional<CourseEntity> course = courseRepository.findById(id);
        if (course.isPresent()) {
            try {
                courseRepository.delete(course.get());

                // Create a response object with the success message
                response.put("message", "Course Deleted successfully.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (Exception e) {
                // Create a response object with the success message
                response.put("message", "Something went wrong.");
                response.put("error", e.getMessage());
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            /// Create a response object with the success message
            response.put("message", "Course Not Found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Fetch all courses with day and time information
    public List<CourseResponse> getAll() {
        List<CourseEntity> courses = courseRepository.findAll();
        return courses.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Get all recent courses
    public List<CourseResponse> getAllRecent() {
        List<CourseEntity> courses = courseRepository.findByOrderByStartDateDesc();
        return courses.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Get all discounted courses
    public List<CourseResponse> getAllDiscount() {
        List<CourseEntity> courses = courseRepository.findByDiscountGreaterThan(0);
        return courses.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Get all top-rating courses
    public List<CourseResponse> getAllTopRating() {
        List<CourseEntity> courses = courseRepository.findTopRatedCourses();
        return courses.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Convert CourseEntity to CourseDTO
    private CourseResponse convertToDTO(CourseEntity course) {
        CourseResponse dto = new CourseResponse();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        double newPrice = 0;
        double price = course.getPrice();
        int discount = course.getDiscount();
        newPrice = price - ((price * discount) / 100);
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


    // Add course to favorite
    public ResponseEntity<Object> addToFavorite(int courseId, UserEntity user) {
        Map<String, String> response = new HashMap<>();

        Optional<CourseEntity> course = courseRepository.findById(courseId);
        if (course.isPresent()) {
            FavoriteEntity favorite = new FavoriteEntity();
            favorite.setUser(user);
            favorite.setCourse(course.get());
            favoriteRepository.save(favorite);

            // Create a response object with the success message
            response.put("message", "Course added to favorite successfully.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            // Create a response object with the success message
            response.put("message", "Course Not Found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Remove course from favorite
    public ResponseEntity<Object> deleteFromFavorite(int courseId, UserEntity user) {
        Map<String, String> response = new HashMap<>();
        Optional<CourseEntity> course = courseRepository.findById(courseId);
        if (course.isPresent()) {
            FavoriteEntity favorite = favoriteRepository.findByUserAndCourse(user, course.get());
            if (favorite != null) {
                favoriteRepository.delete(favorite);

                // Create a response object with the success message
                response.put("message", "Course deleted from favorite successfully.");
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else {
                // Create a response object with the success message
                response.put("message", "This Course is Not in Your Favorite.");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } else {
            // Create a response object with the success message
            response.put("message", "Course Not Found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Get Course Rate
    public ResponseEntity<Object> getRate(int courseId) throws JsonProcessingException {
        Map<String, String> response = new HashMap<>();
        Optional<CourseEntity> courseOpt = courseRepository.findById(courseId);

        if (courseOpt.isPresent()) {
            List<EnrollCourseEntity> enrollments = enrollCourseRepository.findByCourseId(courseId);

            // Calculate average rate
            Double averageRate = enrollments.stream()
                    .flatMapToDouble(e -> DoubleStream.of(e.getRate()))
                    .average()
                    .orElse(0.0); // Return 0.0 if there are no rates

            // Build response
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(averageRate);
            response.put("Rate", jsonResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            // Create a response object with the success message
            response.put("message", "Course Not Found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    //QR Attendance
    public ResponseEntity<Object> qrAttendance(AttendanceRequest body, int id) {
        Map<String, String> response = new HashMap<>();
        Optional<CourseEntity> course = courseRepository.findById(id);
        Optional<UserEntity> student = userRepository.findById(body.getStd_id());
        Optional<EnrollCourseEntity> enroll = enrollCourseRepository.findByUserIdAndCourseId(student.get().getId(), body.getStd_id());
        if (!enroll.isPresent()) {
            // Create a response object with the success message
            response.put("message", "This Student Does Not Enrolled In This Course.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        if (course.isPresent() && student.isPresent() && student.get().getAccountType() == UserAccountEnum.USER) {
            AttendanceEntity attendance = new AttendanceEntity();
            attendance.setCourse(course.get());
            attendance.setUser(student.get());
            attendance.setQr(body.getDate());
            attendance.setPresent(true);
            attendance.setAttDate(LocalDateTime.now());
            attendanceRepository.save(attendance);

            // Create a response object with the success message
            response.put("message", "Thank you for attendance :)");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        // Create a response object with the success message
        response.put("message", "Course Not Found.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    //Manual Attendance
    public ResponseEntity<Object> manualAttendance(EnrollRequest body, int id) {
        Map<String, String> response = new HashMap<>();
        Optional<CourseEntity> course = courseRepository.findById(id);
        Optional<UserEntity> student = userRepository.findById(body.getStd_id());
        Optional<EnrollCourseEntity> enroll = enrollCourseRepository.findByUserIdAndCourseId(student.get().getId(), body.getStd_id());
        if (!enroll.isPresent()) {
            // Create a response object with the success message
            response.put("message", "This Student Does Not Enrolled In This Course.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        if (course.isPresent() && student.isPresent() && student.get().getAccountType() == UserAccountEnum.USER) {
            AttendanceEntity attendance = new AttendanceEntity();
            attendance.setCourse(course.get());
            attendance.setUser(student.get());
            attendance.setQr(null);
            attendance.setPresent(true);
            attendance.setAttDate(LocalDateTime.now());
            attendanceRepository.save(attendance);

            // Create a response object with the success message
            response.put("message", "Thank you for attendance :)");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        // Create a response object with the success message
        response.put("message", "Course Not Found.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    //Add Time and Day for A course
    public ResponseEntity<Object> addDay(DayCourseRequest body, int id) {
        Map<String, String> response = new HashMap<>();

        Optional<CourseEntity> course = courseRepository.findById(id);
        Optional<DayEntity> day = dayRepository.findById(body.getDay_id());
        if (course.isPresent()) {
            CourseDayEntity courseDay = new CourseDayEntity();
            courseDay.setCourse(course.get());
            courseDay.setDay(day.get());
            courseDay.setCourseTime(body.isTime());
            courseDayRepository.save(courseDay);

            // Create a response object with the success message
            response.put("message", "Day & Time added successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            // Create a response object with the success message
            response.put("message", "Course Not Found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    //update Time and Day for A course
    public ResponseEntity<Object> updateDay(DayCourseRequest body, int id) {
        Map<String, String> response = new HashMap<>();

        Optional<DayEntity> day = dayRepository.findById(id);
        Optional<CourseDayEntity> courseDay = courseDayRepository.findByCourseIdAndDayId(id, body.getDay_id());
        if (courseDay.isPresent()) {
            courseDay.get().setDay(day.get());
            courseDay.get().setCourseTime(body.isTime());
            courseDayRepository.save(courseDay.get());

            // Create a response object with the success message
            response.put("message", "Day & Time updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            // Create a response object with the success message
            response.put("message", "Course Day Not Found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    //delete Time and Day for A course
    public ResponseEntity<Object> deleteDay(DayCourseRequest body, int id) {
        Map<String, String> response = new HashMap<>();

        Optional<CourseDayEntity> courseDay = courseDayRepository.findByCourseIdAndDayId(id, body.getDay_id());
        if (courseDay.isPresent()) {
            courseDayRepository.delete(courseDay.get());

            // Create a response object with the success message
            response.put("message", "Course Day Deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            // Create a response object with the success message
            response.put("message", "Course Not Found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

    }

    public ResponseEntity<?> uploadMarksFile(UploadMarksRequest data) {
        //Check User Role
        if (HandleCurrentUserSession.getCurrentUserRole().equals(UserAccountEnum.ADMIN)) {
            MultipartFile marks = data.getMarksFile();

            if (marks == null || marks.isEmpty()) {
                return new ResponseEntity<>("Please upload a marks!", HttpStatus.BAD_REQUEST);
            }

            if (courseRepository.findById(data.getCourseId()).isEmpty()) {
                return new ResponseEntity<>("Course With this Id Not Found", HttpStatus.BAD_REQUEST);
            }

            if (!Objects.requireNonNull(Objects.requireNonNull(marks).getOriginalFilename()).endsWith(".xlsx")) {
                return new ResponseEntity<>("Please upload an Excel File!", HttpStatus.BAD_REQUEST);
            }

            List<Map<String, String>> result = MarkService.handleExcelFile(marks);
            if (result.isEmpty()) {
                return new ResponseEntity<>("Failed to process the file", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            // Upload File To Server
            String resultPath = FilesManagement.uploadSingleFile(marks);
            if (resultPath != null) {
                // Initialize Mark Object
                MarkEntity markEntity = new MarkEntity();
                markEntity.setUser(HandleCurrentUserSession.getCurrentUser());
                markEntity.setCourse(CourseEntity.builder().id(data.getCourseId()).build());
                markEntity.setFile(resultPath);
                markEntity.setDate(data.getUploadDate());
                // Save File In DataBase
                markRepository.save(markEntity);
                return new ResponseEntity<>("Marks Successfully Uploaded", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Something Went Wrong .. try again later", HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>("Only Admins Can Upload Marks", HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseEntity<?> getUserMarks(GetStudentMarksRequest data) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) authentication.getPrincipal();
        Enum<UserAccountEnum> role = user.getAccountType();

        if (data.getPhone() == null) {
            return new ResponseEntity<>("phone must not be Null", HttpStatus.BAD_REQUEST);
        } else if (role.equals(UserAccountEnum.USER)) {
            if (userRepository.findByPhoneNumber(data.getPhone()).isPresent()) {
                if (enrollCourseRepository.findByUserIdAndCourseId(data.getCourseId(), user.getId()).isPresent()) {
                    if (markRepository.findFilesByCourseId(data.getCourseId()).isPresent()) {
                        String markFilePath = markRepository.findFilesByCourseId(data.getCourseId()).get();
                        List<Map<String, String>> markFile = MarkService.handleExcelFile(MarkService.convertFileToMultipartFile(markFilePath));
                        Map<String, String> result = MarkService.searchInExcelFile(markFile, data.getPhone());
                        return new ResponseEntity<>(result, HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>("Course With this Id Not Found Or Mark File Not Uploaded Yet", HttpStatus.BAD_REQUEST);
                    }
                } else {
                    return new ResponseEntity<>("User Not Enroll In This Course", HttpStatus.FORBIDDEN);
                }
            } else {
                return new ResponseEntity<>("Student With this Phone Not Found", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Get Marks Only For Students", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> addDiscount(DiscountRequest body, int id) {
        Map<String, String> response = new HashMap<>();

        Optional<CourseEntity> course = courseRepository.findById(id);
        if (course.isPresent()) {
            course.get().setDiscount(body.getDiscount());
            courseRepository.save(course.get());

            // Create a response object with the success message
            response.put("message", "Discount added to the course successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            // Create a response object with the success message
            response.put("message", "Course Not Found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

    }

    public ResponseEntity<?> UploadHomeworkFile(UploadHomeworkRequest data) {
        UserEntity currentUser = HandleCurrentUserSession.getCurrentUser();
        if (currentUser.getAccountType().equals(UserAccountEnum.ADMIN) || currentUser.getAccountType().equals(UserAccountEnum.TEACHER)) {
            MultipartFile file = data.getHomeworkFile();
            if (file == null || file.isEmpty()) {
                return new ResponseEntity<>("Please upload a HomeWork File!", HttpStatus.BAD_REQUEST);
            }
            if (courseRepository.findById(data.getCourseId()).isEmpty()) {
                return new ResponseEntity<>("Course With this Id Not Found", HttpStatus.BAD_REQUEST);
            }
            // Upload File To Server
            String resultPath = FilesManagement.uploadSingleFile(file);
            if (resultPath != null) {
                // Initialize Homework Object
                HomeWorkEntity homeWorkEntity = new HomeWorkEntity();
                homeWorkEntity.setUser(HandleCurrentUserSession.getCurrentUser());
                homeWorkEntity.setCourse(CourseEntity.builder().id(data.getCourseId()).build());
                homeWorkEntity.setMedia(resultPath);
                homeWorkEntity.setDate(data.getDate());
                homeWorkEntity.setDescription(data.getDescription());
                // Save File In DataBase
                homeWorkRepository.save(homeWorkEntity);
                return new ResponseEntity<>("Homework Successfully Uploaded", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Something Went Wrong .. try again later", HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>("Only Teachers And Admins Can Upload Homeworks", HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseEntity<?> getCourseHomeworkFile(int id) {
        // Check If The Course Exist
        if (courseRepository.findById(id).isEmpty()) {
            return new ResponseEntity<>("Course With this Id Not Found", HttpStatus.NOT_FOUND);
        }
        // Get Current User
        UserEntity currentUser = HandleCurrentUserSession.getCurrentUser();
        //Check If User Exist
        if (userRepository.findByPhoneNumber(currentUser.getPhoneNumber()).isPresent()) {
            return new ResponseEntity<>("Student With this Phone Not Found", HttpStatus.BAD_REQUEST);
        }
        // Check If This User Enroll In This Course
        if (enrollCourseRepository.findByUserIdAndCourseId(id, currentUser.getId()).isPresent()) {
            List<HomeWorkEntity> homeworks = homeWorkRepository.getByCourseId(id);
            if (homeworks.isEmpty()) {
                return new ResponseEntity<>("No Homeworks For This Course Yet", HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(homeworks, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>("User Not Enroll In This Course", HttpStatus.FORBIDDEN);
        }
    }

    public ResponseEntity<?> getCoursesWithStudentsForTeacher() {
        UserEntity currentUser = HandleCurrentUserSession.getCurrentUser();

        if (currentUser.getAccountType().equals(UserAccountEnum.TEACHER) || currentUser.getAccountType().equals(UserAccountEnum.ADMIN)) {
            List<CourseEntity> teacherCourses = courseRepository.findByUserId(currentUser.getId());
            if (!teacherCourses.isEmpty()) {
                List<Object> result = new ArrayList<>();
                for (CourseEntity course : teacherCourses) {
                    Map<String, Object> storage = new HashMap<>();
                    storage.put("course", course);
                    List<EnrollCourseEntity> enrolledStudents = enrollCourseRepository.findByCourseId(course.getId());
                    if (!enrolledStudents.isEmpty()) {
                        List<UserEntity> students = new ArrayList<>();
                        for (EnrollCourseEntity element : enrolledStudents) {
                            students.add(convertToValidObject(userRepository.findById(element.getUser().getId()).get()));
                        }
                        storage.put("students", students);
                    } else {
                        storage.put("students", new ArrayList<>());
                    }
                    result.add(storage);
                }
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("This Teacher Do not Have Any Course Yet", HttpStatus.NO_CONTENT);
            }
        } else {
            return new ResponseEntity<>("Only For Teacher And Admin", HttpStatus.UNAUTHORIZED);
        }
    }

    private UserEntity convertToValidObject(UserEntity userData) {
        UserEntity user = new UserEntity();
        // Initialize Object
        user.setId(userData.getId());
        user.setFirstName(userData.getFirstName());
        user.setLastName(userData.getLastName());
        user.setEmail(userData.getEmail());
        user.setBio(userData.getBio());
        user.setDob(userData.getDob());
        user.setAccountType(userData.getAccountType());
        user.setGender(userData.getGender());
        user.setPhoneNumber(userData.getPhoneNumber());
        user.setEducation(userData.getEducation());
        return user;
    }

}
