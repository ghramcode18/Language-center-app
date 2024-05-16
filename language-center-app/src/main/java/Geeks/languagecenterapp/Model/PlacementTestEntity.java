package Geeks.languagecenterapp.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "placementTest")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlacementTestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String language;

    private int maxNum;

    private Date date;

    @OneToMany(targetEntity = BookEntity.class ,mappedBy ="placementTest" ,orphanRemoval = true)
    private List<BookEntity> bookList ;
}
