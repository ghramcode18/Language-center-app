package Geeks.languagecenterapp.DTO.Response;

import Geeks.languagecenterapp.Model.CourseEntity;
import Geeks.languagecenterapp.Model.CourseImageEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@Data
public class ServiceWithCourseResponse {
    private int ServiceId;
    private String name;
    private List<CourseImageEntity> image;
    private List<CourseResponse> courses;

    // Constructor with serviceId first
    public ServiceWithCourseResponse(int serviceId, String name, List<CourseImageEntity> image ,List<CourseResponse> courses) {
        this.ServiceId = serviceId;
        this.name = name;
        this.image = image;
        this.courses = courses;
    }

}
