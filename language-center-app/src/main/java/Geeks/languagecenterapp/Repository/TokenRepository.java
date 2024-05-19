package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity,Integer> {
}
