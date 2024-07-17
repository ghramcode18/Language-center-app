package Geeks.languagecenterapp.DTO.Response;

import Geeks.languagecenterapp.Model.Enum.GenderEnum;
import Geeks.languagecenterapp.Model.ImageEntity;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
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

    private List<ImageEntity> images;

}