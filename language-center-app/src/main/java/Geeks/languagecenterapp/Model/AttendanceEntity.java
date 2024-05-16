package Geeks.languagecenterapp.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "attendance")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AttendanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(targetEntity = CourseEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private CourseEntity course;

    private String qr;

    private boolean isPresent;

    private Date attDate;
}
