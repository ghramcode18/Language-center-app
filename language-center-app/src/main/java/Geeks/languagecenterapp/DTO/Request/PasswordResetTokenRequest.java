package Geeks.languagecenterapp.DTO.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;



@Setter
@Getter
@Data
public class PasswordResetTokenRequest {
    @NotBlank
    @Email
    private String email;

    private String code;

    private String newPassword;

}
