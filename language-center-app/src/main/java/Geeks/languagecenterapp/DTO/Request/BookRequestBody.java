package Geeks.languagecenterapp.DTO.Request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookRequestBody {

    private String firstName;

    private String lastName;

    private String phoneNumber;

}
