package Geeks.languagecenterapp.DTO.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.bridge.IMessage;
import org.springframework.lang.NonNull;

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
}
