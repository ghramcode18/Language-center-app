package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Integer> {

    List<TokenEntity> findTokenByUserId(Integer id);

    Optional<TokenEntity> findByToken(String token);

}