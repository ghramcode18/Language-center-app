package Geeks.languagecenterapp.DTO.Response;

import Geeks.languagecenterapp.Model.CourseImageEntity;
import Geeks.languagecenterapp.Model.ImageEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseResponse {
    private String title;
    private String description;
    private double price;
    private int numOfHours;
    private int numOfSessions;
    private int numOfRoom;
    private LocalDateTime startDate;
    private double progress;
    private String level;
    private List<CourseDayResponse> courseDayList;
    private CourseImageEntity image;
}
