package Geeks.languagecenterapp.DTO.Response;

import Geeks.languagecenterapp.Model.CourseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@Data
public class ServiceWithCourseResponse {
    private int ServiceId;
    private String name;
    private List<CourseResponse> courses;

    // Constructor with serviceId first
    public ServiceWithCourseResponse(int serviceId, String name, List<CourseResponse> courses) {
        this.ServiceId = serviceId;
        this.name = name;
        this.courses = courses;
    }

}
