package Geeks.languagecenterapp.Model;

import Geeks.languagecenterapp.Model.Enum.ImageEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "image")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private ImageEnum type;

    @Column(nullable = true)
    private String CrtName;

    private String imgUrl;
}
