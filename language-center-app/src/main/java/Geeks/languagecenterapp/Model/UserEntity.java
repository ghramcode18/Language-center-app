package Geeks.languagecenterapp.Model;

import Geeks.languagecenterapp.Model.Enum.GenderEnum;
import Geeks.languagecenterapp.Model.Enum.UserAccountEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Builder
@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String bio;

    @Column(name = "date_of_birth")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    private GenderEnum gender;

    private String education;

    @Column(unique = true)
    private String phoneNumber;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String password;

    private UserAccountEnum accountType;

    private boolean isActive;


    @Getter(AccessLevel.NONE)
    @OneToMany(targetEntity = TokenEntity.class ,mappedBy ="user" ,orphanRemoval = true)
    private List<TokenEntity> tokens ;

    @Getter(AccessLevel.NONE)
    @OneToMany(targetEntity = ImageEntity.class ,mappedBy ="user" ,orphanRemoval = true)
    private List<ImageEntity> images ;

    @Getter(AccessLevel.NONE)
    @OneToMany(targetEntity = NotificationEntity.class ,mappedBy ="user" ,orphanRemoval = true)
    private List<NotificationEntity> notifications ;

    @Getter(AccessLevel.NONE)
    @OneToMany(targetEntity = UserRoleEntity.class ,mappedBy ="user" ,orphanRemoval = true)
    private List<UserRoleEntity> userRoles ;

    @Getter(AccessLevel.NONE)
    @OneToMany(targetEntity = BookEntity.class ,mappedBy ="user" ,orphanRemoval = true)
    private List<BookEntity> bookList ;

    @Getter(AccessLevel.NONE)
    @OneToMany(targetEntity = CourseEntity.class ,mappedBy ="user" ,orphanRemoval = true)
    private List<CourseEntity> courseList ;

    @Getter(AccessLevel.NONE)
    @OneToMany(targetEntity = EnrollCourseEntity.class ,mappedBy ="user" ,orphanRemoval = true)
    private List<EnrollCourseEntity> enrolledCourseList ;

    @Getter(AccessLevel.NONE)
    @OneToMany(targetEntity = FavoriteEntity.class ,mappedBy ="user" ,orphanRemoval = true)
    private List<FavoriteEntity> favoriteList ;

    @Getter(AccessLevel.NONE)
    @OneToMany(targetEntity = HomeWorkEntity.class ,mappedBy ="user" ,orphanRemoval = true)
    private List<HomeWorkEntity> homeWorkList ;

    @Getter(AccessLevel.NONE)
    @OneToMany(targetEntity = AttendanceEntity.class ,mappedBy ="user" ,orphanRemoval = true)
    private List<AttendanceEntity> AttendanceList ;

    @Getter(AccessLevel.NONE)
    @OneToMany(targetEntity = MarkEntity.class ,mappedBy ="user" ,orphanRemoval = true)
    private List<MarkEntity> marks ;

}