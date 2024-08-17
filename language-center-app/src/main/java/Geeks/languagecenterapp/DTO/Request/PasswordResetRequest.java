package Geeks.languagecenterapp.DTO.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class PasswordResetRequest {
    @NotBlank
    @Email
    private String email;
}