package Geeks.languagecenterapp.Controller;

import Geeks.languagecenterapp.Model.Enum.Post;
import Geeks.languagecenterapp.Model.Enum.UserAccountEnum;
import Geeks.languagecenterapp.Model.PostEntity;
import Geeks.languagecenterapp.Model.UserEntity;
import Geeks.languagecenterapp.Service.PlacementTestService;
import Geeks.languagecenterapp.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@AllArgsConstructor
@RequestMapping("api/auth")
public class UserController {

    @Autowired
    private UserService userService;
    //@GetMapping("/profile")
    //  public UserEntity getUserProfile (@AuthenticationPrincipal UserEntity user){
    //    return user ;
    //}

    @GetMapping("/showEvent")
    public List<PostEntity> getEvent (@PathVariable String type) throws Exception {
        if(type.equals(Post.EVENT))
            return userService.getPosts(type) ;
        else throw new Exception("please write a valid type");
    }
    @GetMapping("/showAds")
    public List<PostEntity> getAds (@PathVariable String type) throws Exception {
        if(type.equals(Post.ADS))
            return userService.getPosts(type) ;
        else throw new Exception("please write a valid type");
    }
    @GetMapping("/showTeachers")
    public List<UserEntity> getTeachers (@PathVariable String accountType) throws Exception {
        if(accountType.equals(UserAccountEnum.TEACHER))
            return userService.getUsers(accountType) ;
        else throw new Exception("please write a valid type");

    }  @GetMapping("/showStudents")
    public List<UserEntity> getStudent (@PathVariable  String accountType) throws Exception {
        if(accountType.equals(UserAccountEnum.USER))
            return userService.getUsers(accountType) ;
        else throw new Exception("please write a valid type");
    }
}
