package Geeks.languagecenterapp.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "userId", nullable = true)
    private UserEntity user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "courseId", nullable = true)
    private CourseEntity course;

    private String media;

    @Column(nullable = true)
    private String description;
}
