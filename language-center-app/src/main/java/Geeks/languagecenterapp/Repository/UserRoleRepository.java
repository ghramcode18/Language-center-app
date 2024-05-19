package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity,Integer> {
}
