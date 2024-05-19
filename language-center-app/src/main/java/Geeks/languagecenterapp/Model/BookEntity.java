package Geeks.languagecenterapp.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "placementTest")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(targetEntity = PlacementTestEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "placement_id")
    private PlacementTestEntity placementTest;

    private Date bookingDate;
}
