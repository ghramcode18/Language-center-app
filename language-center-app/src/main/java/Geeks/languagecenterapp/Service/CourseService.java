package Geeks.languagecenterapp.Service;

import Geeks.languagecenterapp.DTO.Request.AttendanceRequest;
import Geeks.languagecenterapp.DTO.Request.CourseRequest;
import Geeks.languagecenterapp.DTO.Request.DayCourseRequest;
import Geeks.languagecenterapp.DTO.Request.EnrollRequest;
import Geeks.languagecenterapp.DTO.Response.CourseDayResponse;
import Geeks.languagecenterapp.DTO.Response.CourseResponse;
import Geeks.languagecenterapp.Model.*;
import Geeks.languagecenterapp.Repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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


    //Add Course by admin and return ok , return bad request response otherwise
    public ResponseEntity<Object> add(CourseRequest courseRequest) throws JsonProcessingException {
        try {
            CourseEntity course = new CourseEntity();
            Optional<UserEntity> teacher = userRepository.findById(courseRequest.getTeacher_id());
            Optional<ServiceEntity> service=serviceRepository.findById(courseRequest.getService_id());
            if (teacher.isPresent() && service.isPresent()) {
                course.setUser(teacher.get());
                course.setService(service.get());
                course.setTitle(courseRequest.getTitle());
                course.setDescription(courseRequest.getDescription());
                course.setPrice(courseRequest.getPrice());
                course.setNumOfHours(courseRequest.getNumOfHours());
                course.setNumOfSessions(courseRequest.getNumOfSessions());
                course.setNumOfRoom(courseRequest.getNumOfRoom());
                course.setStartDate(courseRequest.getStartDate());
                course.setLevel(courseRequest.getLevel());
                courseRepository.save(course);
            }

            // Create a response object with the success message
            String successMessage = "Course added successfully.";
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(successMessage);
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        } catch (Exception e) {
            // Create a response object with the error message
            String errorMessage = "Something went wrong.";
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(errorMessage);
            return new ResponseEntity<>(jsonResponse, HttpStatus.BAD_REQUEST);
        }
    }

    //Search for Course by id ...if found -> update info ...else return not found response
    public ResponseEntity<Object> update(CourseRequest courseRequest, int id) throws JsonProcessingException {
        Optional<CourseEntity> course = courseRepository.findById(id);
        if (course.isPresent()) {
            try {
                Optional<UserEntity> user = userRepository.findById(courseRequest.getTeacher_id());
                Optional<ServiceEntity> service=serviceRepository.findById(courseRequest.getService_id());
                if (user.isPresent() && service.isPresent()) {
                    course.get().setUser(user.get());
                    course.get().setService(service.get());
                    course.get().setTitle(courseRequest.getTitle());
                    course.get().setDescription(courseRequest.getDescription());
                    course.get().setPrice(courseRequest.getPrice());
                    course.get().setNumOfHours(courseRequest.getNumOfHours());
                    course.get().setNumOfSessions(courseRequest.getNumOfSessions());
                    course.get().setNumOfRoom(courseRequest.getNumOfRoom());
                    course.get().setStartDate(courseRequest.getStartDate());
                    course.get().setLevel(courseRequest.getLevel());
                    courseRepository.save(course.get());
                    // Create a response object with the success message
                    String successMessage = "Course updated successfully.";
                    ObjectMapper objectMapper = new ObjectMapper();
                    String jsonResponse = objectMapper.writeValueAsString(successMessage);
                    return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
                }
                else {
                    // Create a response object with the success message
                    String successMessage = "Some error occurred.";
                    ObjectMapper objectMapper = new ObjectMapper();
                    String jsonResponse = objectMapper.writeValueAsString(successMessage);
                    return new ResponseEntity<>(jsonResponse, HttpStatus.BAD_REQUEST);
                }

            } catch (Exception e) {
                // Create a response object with the error message
                String errorMessage = "Something went wrong.";
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonResponse = objectMapper.writeValueAsString(errorMessage);
                return new ResponseEntity<>(jsonResponse, HttpStatus.BAD_REQUEST);
            }
        } else {
            // Create a response object with the not found message
            String notFoundMessage = "Course not found.";
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(notFoundMessage);
            return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
        }
    }

    //Search for Course by id ...if found -> delete info ...else return not found response
    public ResponseEntity<Object> delete(int id) throws JsonProcessingException {
        Optional<CourseEntity> course = courseRepository.findById(id);
        if (course.isPresent()) {
            try {
                courseRepository.delete(course.get());

                // Create a response object with the success message
                String successMessage = "Course deleted successfully.";
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonResponse = objectMapper.writeValueAsString(successMessage);
                return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
            } catch (Exception e) {
                // Create a response object with the error message
                String errorMessage = "Something went wrong.";
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonResponse = objectMapper.writeValueAsString(errorMessage);
                return new ResponseEntity<>(jsonResponse, HttpStatus.BAD_REQUEST);
            }
        } else {
            // Create a response object with the not found message
            String notFoundMessage = "Course not found.";
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(notFoundMessage);
            return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
        }
    }
    // Fetch all courses with day and time information
    public List<CourseResponse> getAll() {
        List<CourseEntity> courses = courseRepository.findAll();
        return courses.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Convert CourseEntity to CourseDTO
    private CourseResponse convertToDTO(CourseEntity course) {
        CourseResponse dto = new CourseResponse();
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        dto.setPrice(course.getPrice());
        dto.setNumOfHours(course.getNumOfHours());
        dto.setNumOfSessions(course.getNumOfSessions());
        dto.setNumOfRoom(course.getNumOfRoom());
        dto.setStartDate(course.getStartDate());
        dto.setProgress(course.getProgress());
        dto.setLevel(course.getLevel());

        List<CourseDayResponse> courseDayDTOs = course.getCourseDayList().stream().map(this::convertToCourseDayDTO).collect(Collectors.toList());
        dto.setCourseDayList(courseDayDTOs);
        return dto;
    }

    // Convert CourseDayEntity to CourseDayDTO
    // Convert CourseDayEntity to CourseDayDTO
    private CourseDayResponse convertToCourseDayDTO(CourseDayEntity courseDay) {
        CourseDayResponse dto = new CourseDayResponse();
        dto.setId(courseDay.getId());
        dto.setDay(courseDay.getDay().getDay());
        dto.setCourseTime(courseDay.isCourseTime() ? "Morning" : "Evening");
        return dto;
    }


    // Add course to favorite
    public ResponseEntity<Object> addToFavorite(int courseId, UserEntity user) throws JsonProcessingException {
        Optional<CourseEntity> course = courseRepository.findById(courseId);
        if (course.isPresent()) {
            FavoriteEntity favorite = new FavoriteEntity();
            favorite.setUser(user);
            favorite.setCourse(course.get());
            favoriteRepository.save(favorite);

            String successMessage = "Course added to favorites.";
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(successMessage);
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        } else {
            String notFoundMessage = "Course not found.";
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(notFoundMessage);
            return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
        }
    }

    // Remove course from favorite
    public ResponseEntity<Object> deleteFromFavorite(int courseId, UserEntity user) throws JsonProcessingException {
        Optional<CourseEntity> course = courseRepository.findById(courseId);
        if (course.isPresent()) {
            FavoriteEntity favorite = favoriteRepository.findByUserAndCourse(user, course.get());
            if (favorite != null) {
                favoriteRepository.delete(favorite);

                String successMessage = "Course removed from favorites.";
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonResponse = objectMapper.writeValueAsString(successMessage);
                return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
            } else {
                String notFoundMessage = "Favorite not found.";
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonResponse = objectMapper.writeValueAsString(notFoundMessage);
                return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
            }
        } else {
            String notFoundMessage = "Course not found.";
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(notFoundMessage);
            return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
        }
    }

    // Get Course Rate
    public ResponseEntity<Object> getRate(int courseId) throws JsonProcessingException {
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
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found.");
        }
    }
    //QR Attendance
    public ResponseEntity<Object> qrAttendance(AttendanceRequest body ,int id) throws JsonProcessingException {
        Optional<CourseEntity> course = courseRepository.findById(id);
        Optional<UserEntity> student = userRepository.findById(body.getStd_id());
        if (course.isPresent()) {
            AttendanceEntity attendance = new AttendanceEntity();
            attendance.setCourse(course.get());
            attendance.setUser(student.get());
            attendance.setQr(body.getQr());
            attendance.setPresent(true);
            attendance.setAttDate(LocalDateTime.now());
            attendanceRepository.save(attendance);

            String successMessage = "Thank you for attendance.";
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(successMessage);
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        }
        String successMessage = "Some Thing Went wrong.";
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(successMessage);
        return new ResponseEntity<>(jsonResponse, HttpStatus.BAD_REQUEST);
    }
    //Manual Attendance
    public ResponseEntity<Object> manualAttendance(EnrollRequest body, int id) throws JsonProcessingException {
        Optional<CourseEntity> course = courseRepository.findById(id);
        Optional<UserEntity> student = userRepository.findById(body.getStd_id());
        if (course.isPresent()) {
            AttendanceEntity attendance = new AttendanceEntity();
            attendance.setCourse(course.get());
            attendance.setUser(student.get());
            attendance.setQr(null);
            attendance.setPresent(true);
            attendance.setAttDate(LocalDateTime.now());
            attendanceRepository.save(attendance);

            String successMessage = "Thank you for attendance.";
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(successMessage);
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        }
        String successMessage = "Some Thing Went wrong.";
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(successMessage);
        return new ResponseEntity<>(jsonResponse, HttpStatus.BAD_REQUEST);
    }
    //Add Time and Day for A course
    public ResponseEntity<Object> addDay(DayCourseRequest body, int id) throws JsonProcessingException {
        Optional<CourseEntity> course = courseRepository.findById(id);
        Optional<DayEntity> day =dayRepository.findById(body.getDay_id());
        if (course.isPresent()) {
            CourseDayEntity courseDay = new CourseDayEntity();
            courseDay.setCourse(course.get());
            courseDay.setDay(day.get());
            courseDay.setCourseTime(body.isTime());
            courseDayRepository.save(courseDay);

            String successMessage = "Day & Time added to course.";
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(successMessage);
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        }
        else {
            String successMessage = "Course not found.";
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(successMessage);
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        }
    }
    //update Time and Day for A course
    public ResponseEntity<Object> updateDay(DayCourseRequest body, int id) throws JsonProcessingException {
        Optional<DayEntity> day = dayRepository.findById(id);
        Optional<CourseDayEntity> courseDay = courseDayRepository.findByCourseIdAndDayId(id,body.getDay_id());
        if (courseDay.isPresent()) {
            courseDay.get().setDay(day.get());
            courseDay.get().setCourseTime(body.isTime());
            courseDayRepository.save(courseDay.get());

            String successMessage = "Day & Time updated to course.";
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(successMessage);
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        }
        else {
            String successMessage = "Some Thing Went wrong.";
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(successMessage);
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        }
    }
    //delete Time and Day for A course
    public ResponseEntity<Object> deleteDay(DayCourseRequest body, int id) throws JsonProcessingException {
        Optional<CourseDayEntity> courseDay = courseDayRepository.findByCourseIdAndDayId(id,body.getDay_id());
        if (courseDay.isPresent()) {
            courseDayRepository.delete(courseDay.get());

            String successMessage = "Day & Time deleted from course.";
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(successMessage);
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        }
        else {
            String successMessage = "Some Thing Went wrong.";
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(successMessage);
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        }

    }


}
