package Geeks.languagecenterapp.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "placementTest")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlacementTestEntity {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String language;

    private int maxNum;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
    @JsonIgnore
    @OneToMany(targetEntity = BookEntity.class ,mappedBy ="placementTest" ,orphanRemoval = true)
    private List<BookEntity> bookList ;
}
