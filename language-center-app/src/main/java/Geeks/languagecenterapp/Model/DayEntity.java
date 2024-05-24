package Geeks.languagecenterapp.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @OneToMany(mappedBy = "day", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CourseDayEntity> courseDayList ;
}
