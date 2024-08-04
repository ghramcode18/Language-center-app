package Geeks.languagecenterapp.Model;

import Geeks.languagecenterapp.Model.Enum.PostImageEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "courseId", nullable = true)
    private CourseEntity course;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "postId", nullable = true)
    private PostEntity post;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "serviceId", nullable = true)
    private ServiceEntity service;

    private String imgUrl;

    private PostImageEnum imageType;
}
