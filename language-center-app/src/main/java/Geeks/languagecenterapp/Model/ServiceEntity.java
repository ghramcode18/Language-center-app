package Geeks.languagecenterapp.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "service")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(targetEntity = CourseEntity.class ,mappedBy ="service" ,orphanRemoval = true)
    private List<CourseEntity> courses;


}
