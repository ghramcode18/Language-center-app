package Geeks.languagecenterapp.Controller;

import Geeks.languagecenterapp.Model.CourseEntity;
import Geeks.languagecenterapp.Model.Enum.UserAccountEnum;
import Geeks.languagecenterapp.Model.UserEntity;
import Geeks.languagecenterapp.Service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(@AuthenticationPrincipal UserEntity user) {
        return new ResponseEntity<>(userService.userProfile(user), HttpStatus.OK);
    }

    //TODO :api need test
    @GetMapping("/enrolled-courses")
    public List<CourseEntity> getEnrolledCourses(@AuthenticationPrincipal UserEntity user) {
        return userService.getEnrolledCourses(user);
    }

    //TODO :api need test
    @GetMapping("/favorite")
    public List<CourseEntity> getFavoriteCourses(@AuthenticationPrincipal UserEntity user) throws JsonProcessingException {
        return userService.getFavoriteCourses(user);
    }

    @GetMapping("/showTeachers")
    public List<UserEntity> getTeachers(@RequestParam UserAccountEnum accountType) throws Exception {
        if (accountType.equals(UserAccountEnum.TEACHER))
            return userService.getUsers(UserAccountEnum.TEACHER);
        else throw new Exception("please write a valid type");

    }

    @GetMapping("/showStudents")
    public List<UserEntity> getStudent(@RequestParam UserAccountEnum accountType) throws Exception {
        if (accountType.equals(UserAccountEnum.USER))
            return userService.getUsers(UserAccountEnum.USER);
        else throw new Exception("please write a valid type");
    }

}
