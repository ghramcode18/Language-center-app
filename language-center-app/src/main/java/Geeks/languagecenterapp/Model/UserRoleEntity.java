package Geeks.languagecenterapp.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "userRole")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(targetEntity = RoleEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private RoleEntity role;
}
