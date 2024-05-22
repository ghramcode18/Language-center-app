package Geeks.languagecenterapp.Controller;

import Geeks.languagecenterapp.Model.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@AllArgsConstructor
@RequestMapping("api/user")
public class UserController {

    @GetMapping("/profile")
    public UserEntity getUserProfile (@AuthenticationPrincipal UserEntity user){
        return user ;
    }

}
