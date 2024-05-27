package Geeks.languagecenterapp.Controller;

import Geeks.languagecenterapp.Model.Enum.PostEnum;
import Geeks.languagecenterapp.Model.Enum.UserAccountEnum;
import Geeks.languagecenterapp.Model.PostEntity;
import Geeks.languagecenterapp.Model.UserEntity;
import Geeks.languagecenterapp.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@AllArgsConstructor
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping("/profile")
      public UserEntity getUserProfile (@AuthenticationPrincipal UserEntity user){
        return user ;
    }

    @GetMapping("/showTeachers")
    public List<UserEntity> getTeachers (@RequestParam UserAccountEnum accountType) throws Exception {
        if(accountType.equals(UserAccountEnum.TEACHER))
            return userService.getUsers(UserAccountEnum.TEACHER) ;
        else throw new Exception("please write a valid type");

    }  @GetMapping("/showStudents")
    public List<UserEntity> getStudent (@RequestParam  UserAccountEnum accountType) throws Exception {
        if(accountType.equals(UserAccountEnum.USER))
            return userService.getUsers(UserAccountEnum.USER) ;
        else throw new Exception("please write a valid type");
    }
}
