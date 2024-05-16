package Geeks.languagecenterapp.Model;

import Geeks.languagecenterapp.Model.Enum.UserAccountEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "role")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private UserAccountEnum role;

    @OneToMany(targetEntity = UserRoleEntity.class ,mappedBy ="role" ,orphanRemoval = true)
    private List<UserRoleEntity> userRoles ;

    @OneToMany(targetEntity = RolePermissionEntity.class ,mappedBy ="" ,orphanRemoval = true)
    private List<RolePermissionEntity> userPermissions ;

}
