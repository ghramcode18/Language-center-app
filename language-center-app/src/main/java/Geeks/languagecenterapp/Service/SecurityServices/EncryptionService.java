package Geeks.languagecenterapp.Service.SecurityServices;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EncryptionService {

    private final PasswordEncoder passwordEncoder;


    public EncryptionService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String encryptPassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }

    public boolean verifyPassword(String plainPassword, String encryptedPassword) {
        return passwordEncoder.matches(plainPassword, encryptedPassword);
    }

}
