package Geeks.languagecenterapp.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "courseDay")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseDayEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(targetEntity = CourseEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private CourseEntity course;

    @ManyToOne(targetEntity = DayEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "day_id")
    private DayEntity day;

    private boolean courseTime; //morning or evening
}
