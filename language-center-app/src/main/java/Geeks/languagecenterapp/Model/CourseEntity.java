package Geeks.languagecenterapp.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "course")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private UserEntity user;

    @ManyToOne(targetEntity = ServiceEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private ServiceEntity service;
    private String title;

    private String description;

    private double price;

    private int numOfHours;

    private int numOfSessions;

    private int numOfRoom;

    private Date startDate;

    private double progress;

    private boolean isActive;

    private String  level;


    @OneToMany(targetEntity = EnrollCourseEntity.class ,mappedBy ="course" ,orphanRemoval = true)
    private List<EnrollCourseEntity> enrolledCourseList ;

    @OneToMany(targetEntity = FavoriteEntity.class ,mappedBy ="course" ,orphanRemoval = true)
    private List<FavoriteEntity> favoriteList ;

    @OneToMany(targetEntity = HomeWorkEntity.class ,mappedBy ="course" ,orphanRemoval = true)
    private List<HomeWorkEntity> homeWorkList ;

    @OneToMany(targetEntity = AttendanceEntity.class ,mappedBy ="course" ,orphanRemoval = true)
    private List<AttendanceEntity> AttendanceList ;

    @OneToMany(targetEntity = CourseDayEntity.class ,mappedBy ="course" ,orphanRemoval = true)
    private List<CourseDayEntity> courseDayList ;

    @OneToMany(targetEntity = CourseImageEntity.class ,mappedBy ="course" ,orphanRemoval = true)
    private List<CourseImageEntity> courseImageList ;




}
