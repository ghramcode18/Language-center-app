package Geeks.languagecenterapp.DTO.Request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookRequest {

    private String firstName;

    private String lastName;

    private String phoneNumber;

}
