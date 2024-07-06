package Geeks.languagecenterapp.DTO.Request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetStudentMarksRequest {

    Integer courseId;

    String phone;

}