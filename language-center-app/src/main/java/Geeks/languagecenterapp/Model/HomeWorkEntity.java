package Geeks.languagecenterapp.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "homeWork")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HomeWorkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private UserEntity user;

    @ManyToOne(targetEntity = CourseEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private CourseEntity course;

    private String media;

    @Column(nullable = true)
    private String description;
}
