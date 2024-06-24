package Geeks.languagecenterapp.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseRequest {

    private int teacher_id;
    private int service_id;
    private String title;

    private String description;

    private double price;

    private int numOfHours;

    private int numOfSessions;

    private int numOfRoom;

    private String startDate;

    private String  level;

    private MultipartFile image;
}
