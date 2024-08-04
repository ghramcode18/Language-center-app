package Geeks.languagecenterapp.Controller;

import Geeks.languagecenterapp.DTO.Request.*;
import Geeks.languagecenterapp.DTO.Response.CourseResponse;
import Geeks.languagecenterapp.Model.UserEntity;
import Geeks.languagecenterapp.Service.CourseService;
import Geeks.languagecenterapp.Service.MarkService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    //Create Course
    @PostMapping("/add")
    public ResponseEntity<?> addCourse(@ModelAttribute CourseRequest body) throws JsonProcessingException {
        return courseService.add(body);
    }

    //update Course
    @PostMapping("/update/{id}")
    public ResponseEntity<Object> updateCourse(@PathVariable("id") int id, @ModelAttribute CourseRequest body) throws JsonProcessingException {
        return courseService.update(body, id);
    }

    //delete Course
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteCourse(@PathVariable("id") int id) throws JsonProcessingException {
        return courseService.delete(id);
    }

    //Add Time and Day for A course
    @PostMapping("/add-day/{id}")
    public ResponseEntity<Object> addTimeDay(@PathVariable("id") int id, @ModelAttribute DayCourseRequest body) throws JsonProcessingException {
        return courseService.addDay(body, id);
    }

    //update Time and Day for A course
    @PostMapping("/update-day/{id}")
    public ResponseEntity<Object> updateTimeDay(@PathVariable("id") int id, @ModelAttribute DayCourseRequest body) throws JsonProcessingException {
        return courseService.updateDay(body, id);
    }

    //delete Time and Day for A course
    @DeleteMapping("/delete-day/{id}")
    public ResponseEntity<Object> deleteTimeDay(@PathVariable("id") int id, @ModelAttribute DayCourseRequest body) throws JsonProcessingException {
        return courseService.deleteDay(body, id);
    }

    //get All Courses
    @GetMapping("/get/all")
    public ResponseEntity<List<CourseResponse>> getAllCourses() {
        List<CourseResponse> courses = courseService.getAll();
        return ResponseEntity.ok(courses);
    }

    // Add Course to Favorite
    @PostMapping("/add-to-favorite/{id}")
    public ResponseEntity<Object> addCourseToFavorite(@PathVariable("id") int id, @AuthenticationPrincipal UserEntity user) throws JsonProcessingException {
        return courseService.addToFavorite(id, user);
    }

    // Delete Course from Favorite
    @PostMapping("/remove-from-favorite/{id}")
    public ResponseEntity<Object> deleteCourseFromFavorite(@PathVariable("id") int id, @AuthenticationPrincipal UserEntity user) throws JsonProcessingException {
        return courseService.deleteFromFavorite(id, user);
    }

    // get Course Rate
    @GetMapping("/get-course-rate/{id}")
    public ResponseEntity<Object> getCourseRate(@PathVariable("id") int id) throws JsonProcessingException {
        return courseService.getRate(id);

    }

    // QR Attendance
    @PostMapping("/qr-attendance/{id}")
    public ResponseEntity<Object> qrAttendance(@PathVariable("id") int id, @ModelAttribute AttendanceRequest body) throws JsonProcessingException {
        return courseService.qrAttendance(body, id);
    }

    //Manual Attendance
    @PostMapping("/manual-attendance/{id}")
    public ResponseEntity<Object> manualAttendance(@PathVariable("id") int id, @ModelAttribute EnrollRequest body) throws JsonProcessingException {
        return courseService.manualAttendance(body, id);
    }

    @PostMapping("/uploadMarks")
    public ResponseEntity<?> uploadMarksFile(@ModelAttribute UploadMarksRequset body) {
        return new ResponseEntity<>(courseService.uploadMarksFile(body), HttpStatus.OK);
    }

    @GetMapping("/showStudentMarks")
    public ResponseEntity<?> getStudentMarks(@ModelAttribute GetStudentMarksRequest body) {
        return courseService.getUserMarks(body);
    }


}
