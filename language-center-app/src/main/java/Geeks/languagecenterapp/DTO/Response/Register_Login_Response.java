package Geeks.languagecenterapp.DTO.Response;

import Geeks.languagecenterapp.Model.Enum.GenderEnum;
import lombok.Data;

import java.util.Date;

@Data
public class Register_Login_Response {

    private String message;

    private String firstName;

    private String lastName;

    private String email;

    private GenderEnum gender;

    private String bio;

    private Date dob;

    private String education;

    private String token;

    private String phone;

}