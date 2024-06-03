package Geeks.languagecenterapp.DTO.Request;

import Geeks.languagecenterapp.Model.ServiceEntity;
import Geeks.languagecenterapp.Model.UserEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    private String  level;
}
