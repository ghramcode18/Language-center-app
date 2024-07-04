package Geeks.languagecenterapp.DTO.Response;

import Geeks.languagecenterapp.Model.CourseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServiceWithCourseResponse {
    private int ServiceId;
    private String name;

    private List<CourseResponse> courses;


}
