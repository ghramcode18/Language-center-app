package Geeks.languagecenterapp.DTO.Request;

import Geeks.languagecenterapp.Model.Enum.GenderEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.Date;

@Getter
public class RegisterRequest {

    @NotBlank(message = "firstName is required")
    private String firstName;

    @NotBlank(message = "lastName is required")
    private String lastName;

    @Email(message = "Not Valid Email!", regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")
    @NotBlank(message = "email is required")
    private String email;

    @NotBlank(message = "password is required")
    @Size(min = 8, max = 255, message = "password must be 8 characters at Least ")
    private String password;

    private GenderEnum gender;

    private String phone;

    private String education;

    private String bio;

    private Date dob;

}