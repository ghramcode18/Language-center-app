package Geeks.languagecenterapp.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "courseImage")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(targetEntity = CourseEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id" ,nullable = true)
    private CourseEntity course;

    @ManyToOne(targetEntity = PostEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id" ,nullable = true)
    private PostEntity post;

    private String imgUrl;
}
