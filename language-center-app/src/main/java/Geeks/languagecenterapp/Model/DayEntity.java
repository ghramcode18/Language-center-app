package Geeks.languagecenterapp.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "day")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DayEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String day;

    @OneToMany(targetEntity = CourseDayEntity.class ,mappedBy ="day" ,orphanRemoval = true)
    private List<CourseDayEntity> courseDayList ;
}
