package Geeks.languagecenterapp.Controller;

import Geeks.languagecenterapp.DTO.Request.AttendanceRequest;
import Geeks.languagecenterapp.DTO.Request.CourseRequest;
import Geeks.languagecenterapp.DTO.Request.EnrollRequest;
import Geeks.languagecenterapp.Model.CourseEntity;
import Geeks.languagecenterapp.Model.UserEntity;
import Geeks.languagecenterapp.Service.CourseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course")
public class CourseController {
    @Autowired
    private CourseService courseService;
    //Create Course
    @PostMapping("/add")
    public ResponseEntity<Object> addCourse(@RequestBody CourseRequest body) throws JsonProcessingException {
        return courseService.add(body);
    }
    //update Course
    @PostMapping("/update/{id}")
    public ResponseEntity<Object>updateCourse(@PathVariable("id") int id , @RequestBody CourseRequest body)throws JsonProcessingException{
        return courseService.update(body, id);
    }
    //delete Course
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteCourse(@PathVariable("id") int id ) throws JsonProcessingException {
        return courseService.delete(id);
    }
    //get All Courses
    @GetMapping("/get/all")
    public ResponseEntity<List<CourseEntity>> getAllCourses() throws JsonProcessingException {
        return ResponseEntity.ok(courseService.getAll());
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
    public ResponseEntity<Object> getCourseRate(@PathVariable("id") int id ) throws JsonProcessingException {
        return courseService.getRate(id);

    }
    // QR Attendance
    @PostMapping("/qr-attendance/{id}")
    public ResponseEntity<Object> qrAttendance(@PathVariable("id") int id ,@RequestBody AttendanceRequest body) throws JsonProcessingException {
        return courseService.qrAttendance(body,id);
    }
    //Manual Attendance
    @PostMapping("/manual-attendance/{id}")
    public ResponseEntity<Object> manualAttendance(@PathVariable("id") int id ,@RequestBody EnrollRequest body) throws JsonProcessingException {
        return courseService.manualAttendance(body,id);
    }


}
