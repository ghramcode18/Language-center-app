package Geeks.languagecenterapp.DTO.Response;

import Geeks.languagecenterapp.Model.Enum.GenderEnum;
import Geeks.languagecenterapp.Model.Enum.UserAccountEnum;
import Geeks.languagecenterapp.Model.ImageEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class Register_Login_Response {

    private String message;

    private String firstName;

    private String lastName;

    private String email;

    private GenderEnum gender;

    private String bio;

    private LocalDate dob;

    private String education;

    private String token;

    private String phone;

    private UserAccountEnum role ;

    private List<ImageEntity> images;

}