package Geeks.languagecenterapp.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "mark")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MarkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "adminId", nullable = true)
    private UserEntity user;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courseId",referencedColumnName = "id")
    private CourseEntity course;

    @Column(nullable = false)
    private String file;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}
