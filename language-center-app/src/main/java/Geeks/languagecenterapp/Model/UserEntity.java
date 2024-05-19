package Geeks.languagecenterapp.Model;

import Geeks.languagecenterapp.Model.Enum.GenderEnum;
import Geeks.languagecenterapp.Model.Enum.UserAccountEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;

    private String lastName;

    @Column(nullable = true)
    private String bio;

    @Column(name = "date_of_bearth")
    private Date dob;

    private GenderEnum gender;

    @Column(nullable = true)
    private String education;

    @Column(nullable = true)
    private String phoneNumber;

    @Column(unique = true)
    private String email;

    private String password;

    private UserAccountEnum accountType;

    private boolean isActive;

    @OneToMany(targetEntity = TokenEntity.class ,mappedBy ="user" ,orphanRemoval = true)
    private List<TokenEntity> tokens ;

    @OneToMany(targetEntity = ImageEntity.class ,mappedBy ="user" ,orphanRemoval = true)
    private List<ImageEntity> images ;

    @OneToMany(targetEntity = NotificationEntity.class ,mappedBy ="user" ,orphanRemoval = true)
    private List<NotificationEntity> notifications ;

    @OneToMany(targetEntity = UserRoleEntity.class ,mappedBy ="user" ,orphanRemoval = true)
    private List<UserRoleEntity> userRoles ;

    @OneToMany(targetEntity = BookEntity.class ,mappedBy ="user" ,orphanRemoval = true)
    private List<BookEntity> bookList ;

    @OneToMany(targetEntity = CourseEntity.class ,mappedBy ="user" ,orphanRemoval = true)
    private List<CourseEntity> courseList ;

    @OneToMany(targetEntity = EnrollCourseEntity.class ,mappedBy ="user" ,orphanRemoval = true)
    private List<EnrollCourseEntity> enrolledCourseList ;

    @OneToMany(targetEntity = FavoriteEntity.class ,mappedBy ="user" ,orphanRemoval = true)
    private List<FavoriteEntity> favoriteList ;

    @OneToMany(targetEntity = HomeWorkEntity.class ,mappedBy ="user" ,orphanRemoval = true)
    private List<HomeWorkEntity> homeWorkList ;

    @OneToMany(targetEntity = AttendanceEntity.class ,mappedBy ="user" ,orphanRemoval = true)
    private List<AttendanceEntity> AttendanceList ;




}
