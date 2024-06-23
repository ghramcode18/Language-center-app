package Geeks.languagecenterapp.Service;

import Geeks.languagecenterapp.DTO.Request.AttendanceRequest;
import Geeks.languagecenterapp.DTO.Request.CourseRequest;
import Geeks.languagecenterapp.DTO.Request.DayCourseRequest;
import Geeks.languagecenterapp.DTO.Request.EnrollRequest;
import Geeks.languagecenterapp.DTO.Response.CourseDayResponse;
import Geeks.languagecenterapp.DTO.Response.CourseResponse;
import Geeks.languagecenterapp.Model.*;
import Geeks.languagecenterapp.Model.Enum.UserAccountEnum;
import Geeks.languagecenterapp.Repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public ResponseEntity<?> add(CourseRequest courseRequest) throws JsonProcessingException {
        Map <String,String> response = new HashMap<>();
        try {
            CourseEntity course = new CourseEntity();
            Optional<UserEntity> teacher = userRepository.findById(courseRequest.getTeacher_id());
            Optional<ServiceEntity> service=serviceRepository.findById(courseRequest.getService_id());
            if (teacher.isPresent() && service.isPresent() && teacher.get().getAccountType() == UserAccountEnum.TEACHER) {
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
                // Create a response object with the success message
                response.put("message","Course added successfully.");
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
            else if(!teacher.isPresent()) {
                // Create a response object with the success message
                response.put("message","Teacher not found.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            else if(!service.isPresent()) {
                // Create a response object with the success message
                response.put("message","Service not found.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            else {
                // Create a response object with the success message
                response.put("message","Something went wrong.");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            // Create a response object with the error message
            response.put("message","Some Error Occurred.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Search for Course by id ...if found -> update info ...else return not found response
    public ResponseEntity<Object> update(CourseRequest courseRequest, int id) throws JsonProcessingException {
        Map <String,String> response = new HashMap<>();

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
                    // Manually parse the startDate from String to LocalDateTime
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime parsedStartDate = LocalDateTime.parse(courseRequest.getStartDate(), formatter);
                    course.get().setStartDate(parsedStartDate);                    course.get().setLevel(courseRequest.getLevel());
                    courseRepository.save(course.get());
                    // Create a response object with the success message
                    // Create a response object with the success message
                    response.put("message","Course updated successfully.");
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
                else {
                    // Create a response object with the success message
                    response.put("message","Something went wrong.");
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }

            } catch (Exception e) {
                // Create a response object with the success message
                response.put("message","Some Error Occurred.");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            // Create a response object with the success message
            response.put("message","Course Not Found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    //Search for Course by id ...if found -> delete info ...else return not found response
    public ResponseEntity<Object> delete(int id) throws JsonProcessingException {
        Map <String,String> response = new HashMap<>();

        Optional<CourseEntity> course = courseRepository.findById(id);
        if (course.isPresent()) {
            try {
                courseRepository.delete(course.get());

                // Create a response object with the success message
                response.put("message","Course Deleted successfully.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (Exception e) {
                // Create a response object with the success message
                response.put("message","Something went wrong.");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            /// Create a response object with the success message
            response.put("message","Course Not Found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
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
    private CourseDayResponse convertToCourseDayDTO(CourseDayEntity courseDay) {
        CourseDayResponse dto = new CourseDayResponse();
        dto.setId(courseDay.getId());
        dto.setDay(courseDay.getDay().getDay());
        dto.setCourseTime(courseDay.isCourseTime() ? "Morning" : "Evening");
        return dto;
    }


    // Add course to favorite
    public ResponseEntity<Object> addToFavorite(int courseId, UserEntity user) throws JsonProcessingException {
        Map <String,String> response = new HashMap<>();

        Optional<CourseEntity> course = courseRepository.findById(courseId);
        if (course.isPresent()) {
            FavoriteEntity favorite = new FavoriteEntity();
            favorite.setUser(user);
            favorite.setCourse(course.get());
            favoriteRepository.save(favorite);

            // Create a response object with the success message
            response.put("message","Course added to favorite successfully.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            // Create a response object with the success message
            response.put("message","Course Not Found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Remove course from favorite
    public ResponseEntity<Object> deleteFromFavorite(int courseId, UserEntity user) throws JsonProcessingException {
        Map <String,String> response = new HashMap<>();
        Optional<CourseEntity> course = courseRepository.findById(courseId);
        if (course.isPresent()) {
            FavoriteEntity favorite = favoriteRepository.findByUserAndCourse(user, course.get());
            if (favorite != null) {
                favoriteRepository.delete(favorite);

                // Create a response object with the success message
                response.put("message","Course deleted from favorite successfully.");
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else {
                // Create a response object with the success message
                response.put("message","This Course is Not in Your Favorite.");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } else {
            // Create a response object with the success message
            response.put("message","Course Not Found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Get Course Rate
    public ResponseEntity<Object> getRate(int courseId) throws JsonProcessingException {
        Map <String,String> response = new HashMap<>();
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
            response.put("Rate",jsonResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            // Create a response object with the success message
            response.put("message","Course Not Found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    //QR Attendance
    public ResponseEntity<Object> qrAttendance(AttendanceRequest body ,int id) throws JsonProcessingException {
        Map <String,String> response = new HashMap<>();
        Optional<CourseEntity> course = courseRepository.findById(id);
        Optional<UserEntity> student = userRepository.findById(body.getStd_id());
        Optional<EnrollCourseEntity> enroll= enrollCourseRepository.findByUserIdAndCourseId(student.get().getId(), body.getStd_id());
        if (!enroll.isPresent()) {
            // Create a response object with the success message
            response.put("message","This Student Does Not Enrolled In This Course.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        if (course.isPresent() && student.isPresent() && student.get().getAccountType()==UserAccountEnum.USER) {
            AttendanceEntity attendance = new AttendanceEntity();
            attendance.setCourse(course.get());
            attendance.setUser(student.get());
            attendance.setQr(body.getQr());
            attendance.setPresent(true);
            attendance.setAttDate(LocalDateTime.now());
            attendanceRepository.save(attendance);

            // Create a response object with the success message
            response.put("message","Thank you for attendance :)");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        // Create a response object with the success message
        response.put("message","Course Not Found.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    //Manual Attendance
    public ResponseEntity<Object> manualAttendance(EnrollRequest body, int id) throws JsonProcessingException {
        Map <String,String> response = new HashMap<>();
        Optional<CourseEntity> course = courseRepository.findById(id);
        Optional<UserEntity> student = userRepository.findById(body.getStd_id());
        Optional<EnrollCourseEntity> enroll= enrollCourseRepository.findByUserIdAndCourseId(student.get().getId(), body.getStd_id());
        if (!enroll.isPresent()) {
            // Create a response object with the success message
            response.put("message","This Student Does Not Enrolled In This Course.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        if (course.isPresent() && student.isPresent() && student.get().getAccountType()==UserAccountEnum.USER) {
            AttendanceEntity attendance = new AttendanceEntity();
            attendance.setCourse(course.get());
            attendance.setUser(student.get());
            attendance.setQr(null);
            attendance.setPresent(true);
            attendance.setAttDate(LocalDateTime.now());
            attendanceRepository.save(attendance);

            // Create a response object with the success message
            response.put("message","Thank you for attendance :)");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        // Create a response object with the success message
        response.put("message","Course Not Found.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    //Add Time and Day for A course
    public ResponseEntity<Object> addDay(DayCourseRequest body, int id) throws JsonProcessingException {
        Map <String,String> response = new HashMap<>();

        Optional<CourseEntity> course = courseRepository.findById(id);
        Optional<DayEntity> day =dayRepository.findById(body.getDay_id());
        if (course.isPresent()) {
            CourseDayEntity courseDay = new CourseDayEntity();
            courseDay.setCourse(course.get());
            courseDay.setDay(day.get());
            courseDay.setCourseTime(body.isTime());
            courseDayRepository.save(courseDay);

            // Create a response object with the success message
            response.put("message","Day & Time added successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            // Create a response object with the success message
            response.put("message","Course Not Found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    //update Time and Day for A course
    public ResponseEntity<Object> updateDay(DayCourseRequest body, int id) throws JsonProcessingException {
        Map <String,String> response = new HashMap<>();

        Optional<DayEntity> day = dayRepository.findById(id);
        Optional<CourseDayEntity> courseDay = courseDayRepository.findByCourseIdAndDayId(id,body.getDay_id());
        if (courseDay.isPresent()) {
            courseDay.get().setDay(day.get());
            courseDay.get().setCourseTime(body.isTime());
            courseDayRepository.save(courseDay.get());

            // Create a response object with the success message
            response.put("message","Day & Time updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            // Create a response object with the success message
            response.put("message","Course Day Not Found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    //delete Time and Day for A course
    public ResponseEntity<Object> deleteDay(DayCourseRequest body, int id) throws JsonProcessingException {
        Map <String,String> response = new HashMap<>();

        Optional<CourseDayEntity> courseDay = courseDayRepository.findByCourseIdAndDayId(id,body.getDay_id());
        if (courseDay.isPresent()) {
            courseDayRepository.delete(courseDay.get());

            // Create a response object with the success message
            response.put("message","Course Day Deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            // Create a response object with the success message
            response.put("message","Course Not Found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

    }


}
