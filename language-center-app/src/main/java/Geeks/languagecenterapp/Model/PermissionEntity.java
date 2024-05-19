package Geeks.languagecenterapp.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "permission")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PermissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;

    @OneToMany(targetEntity = RolePermissionEntity.class ,mappedBy ="permission" ,orphanRemoval = true)
    private List<RolePermissionEntity> userPermissions; ;
}
