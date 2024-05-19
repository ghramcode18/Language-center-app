package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.RolePermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermissionEntity,Integer> {
}
