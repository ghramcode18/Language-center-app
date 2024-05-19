package Geeks.languagecenterapp.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rolePermission")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RolePermissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(targetEntity = RoleEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    @ManyToOne(targetEntity = PermissionEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id")
    private PermissionEntity permission;
}
