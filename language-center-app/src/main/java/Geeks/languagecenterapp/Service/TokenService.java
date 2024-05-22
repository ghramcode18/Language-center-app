package Geeks.languagecenterapp.Service;

import Geeks.languagecenterapp.Model.TokenEntity;
import Geeks.languagecenterapp.Model.UserEntity;
import Geeks.languagecenterapp.Repository.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    public void saveUserToken(UserEntity user, String generatedToken) {
        var token = TokenEntity.builder()
                .user(user).token(generatedToken).build();
        tokenRepository.save(token);
    }

    public void revokeOldUserTokens(UserEntity user) {
        List<TokenEntity> validUserTokens = tokenRepository.findTokenByUserId(user.getId());
        if (!validUserTokens.isEmpty()) {
            validUserTokens.forEach(tokenRepository::delete);
        }
    }

}