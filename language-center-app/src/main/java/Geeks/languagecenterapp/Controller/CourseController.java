package Geeks.languagecenterapp.Controller;

import Geeks.languagecenterapp.DTO.Request.*;
import Geeks.languagecenterapp.DTO.Response.CourseResponse;
import Geeks.languagecenterapp.Model.Enum.UserAccountEnum;
import Geeks.languagecenterapp.Model.UserEntity;
import Geeks.languagecenterapp.Service.CourseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/course")
public class CourseController {
    @Autowired
    private CourseService courseService;
    //Create Course
    @PostMapping("/add")
    public ResponseEntity<?> addCourse( @AuthenticationPrincipal UserEntity user ,@ModelAttribute CourseRequest body) throws JsonProcessingException {
        Map<String, String> response = new HashMap<>();
        if (user.getAccountType()!= UserAccountEnum.ADMIN){
            // Create a response object with the success message
            response.put("message","You are UnAuthorized");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        return courseService.add(body);
    }

    //update Course
    @PostMapping("/update/{id}")
    public ResponseEntity<Object>updateCourse(@AuthenticationPrincipal UserEntity user ,@PathVariable("id") int id , @ModelAttribute CourseRequest body)throws JsonProcessingException{
        Map<String, String> response = new HashMap<>();
        if (user.getAccountType()!= UserAccountEnum.ADMIN){
            // Create a response object with the success message
            response.put("message","You are UnAuthorized");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        return courseService.update(body, id);
    }
    //delete Course
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteCourse(@AuthenticationPrincipal UserEntity user ,@PathVariable("id") int id ) throws JsonProcessingException {
        Map<String, String> response = new HashMap<>();
        if (user.getAccountType()!= UserAccountEnum.ADMIN){
            // Create a response object with the success message
            response.put("message","You are UnAuthorized");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        return courseService.delete(id);
    }
    //Add Time and Day for A course
    @PostMapping("/add-day/{id}")
    public ResponseEntity<Object> addTimeDay(@AuthenticationPrincipal UserEntity user ,@PathVariable("id") int id ,@ModelAttribute DayCourseRequest body) throws JsonProcessingException {
        Map<String, String> response = new HashMap<>();
        if (user.getAccountType()!= UserAccountEnum.ADMIN){
            // Create a response object with the success message
            response.put("message","You are UnAuthorized");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        return courseService.addDay(body,id);
    }
    //update Time and Day for A course
    @PostMapping("/update-day/{id}")
    public ResponseEntity<Object> updateTimeDay(@AuthenticationPrincipal UserEntity user ,@PathVariable("id") int id ,@ModelAttribute DayCourseRequest body) throws JsonProcessingException {
        Map<String, String> response = new HashMap<>();
        if (user.getAccountType()!= UserAccountEnum.ADMIN){
            // Create a response object with the success message
            response.put("message","You are UnAuthorized");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        return courseService.updateDay(body,id);
    }
    //delete Time and Day for A course
    @DeleteMapping("/delete-day/{id}")
    public ResponseEntity<Object> deleteTimeDay(@AuthenticationPrincipal UserEntity user ,@PathVariable("id") int id ,@ModelAttribute DayCourseRequest body) {
        Map<String, String> response = new HashMap<>();
        if (user.getAccountType()!= UserAccountEnum.ADMIN){
            // Create a response object with the success message
            response.put("message","You are UnAuthorized");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        return courseService.deleteDay(body,id);
    }
    //get All Courses
    @GetMapping("/get/all")
    public ResponseEntity<List<CourseResponse>> getAllCourses() {
        List<CourseResponse> courses = courseService.getAll();
        return ResponseEntity.ok(courses);
    }
    //get All Recent Courses
    @GetMapping("/get/all-recent")
    public ResponseEntity<List<CourseResponse>> getAllRecentCourses() {
        List<CourseResponse> courses = courseService.getAllRecent();
        return ResponseEntity.ok(courses);
    }
    //get All Discount Courses
    @GetMapping("/get/all-discount")
    public ResponseEntity<List<CourseResponse>> getAllDiscountCourses() {
        List<CourseResponse> courses = courseService.getAllDiscount();
        return ResponseEntity.ok(courses);
    }
    //get All Top Rating Courses
    @GetMapping("/get/all-top-rating")
    public ResponseEntity<List<CourseResponse>> getAllTopRatingCourses() {
        List<CourseResponse> courses = courseService.getAllTopRating();
        return ResponseEntity.ok(courses);
    }
    // Add Course to Favorite
    @PostMapping("/add-to-favorite/{id}")
    public ResponseEntity<Object> addCourseToFavorite(@PathVariable("id") int id, @AuthenticationPrincipal UserEntity user) {
        Map<String, String> response = new HashMap<>();
        if (user.getAccountType()!= UserAccountEnum.USER){
            // Create a response object with the success message
            response.put("message","You are UnAuthorized");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        return courseService.addToFavorite(id, user);
    }
    // Delete Course from Favorite
    @PostMapping("/remove-from-favorite/{id}")
    public ResponseEntity<Object> deleteCourseFromFavorite(@PathVariable("id") int id, @AuthenticationPrincipal UserEntity user) {
        Map<String, String> response = new HashMap<>();
        if (user.getAccountType()!= UserAccountEnum.USER){
            // Create a response object with the success message
            response.put("message","You are UnAuthorized");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        return courseService.deleteFromFavorite(id, user);
    }
    // get Course Rate
    @GetMapping("/get-course-rate/{id}")
    public ResponseEntity<Object> getCourseRate(@PathVariable("id") int id ) throws JsonProcessingException {
        return courseService.getRate(id);

    }
    // QR Attendance
    @PostMapping("/qr-attendance/{id}")
    public ResponseEntity<Object> qrAttendance(@PathVariable("id") int id ,@ModelAttribute AttendanceRequest body,@AuthenticationPrincipal UserEntity user) {
        Map<String, String> response = new HashMap<>();
        if (user.getAccountType()!= UserAccountEnum.TEACHER){
            // Create a response object with the success message
            response.put("message","You are UnAuthorized");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        return courseService.qrAttendance(body,id);
    }
    //Manual Attendance
    @PostMapping("/manual-attendance/{id}")
    public ResponseEntity<Object> manualAttendance(@PathVariable("id") int id ,@ModelAttribute EnrollRequest body ,@AuthenticationPrincipal UserEntity user) {
        Map<String, String> response = new HashMap<>();
        if (user.getAccountType()!= UserAccountEnum.TEACHER){
            // Create a response object with the success message
            response.put("message","You are UnAuthorized");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        return courseService.manualAttendance(body,id);
    }
    @PostMapping("/add/discount/{id}")
    public ResponseEntity<?> addCourseDiscount(@PathVariable("id") int id , @ModelAttribute DiscountRequest body ,@AuthenticationPrincipal UserEntity user){
        Map<String, String> response = new HashMap<>();
        if (user.getAccountType()!= UserAccountEnum.ADMIN){
            // Create a response object with the success message
            response.put("message","You are UnAuthorized");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        return courseService.addDiscount(body,id);
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
