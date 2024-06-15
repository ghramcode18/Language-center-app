package Geeks.languagecenterapp.DTO.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {

    @Email(message = "Not Valid Email!")
    @NotBlank(message = "Email is required")
    private String email;

    @Size(min = 8, max = 255, message = "Password Must be 8 character at Least")
    @NotBlank(message = "Password is required")
    private String password;

}