package Geeks.languagecenterapp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;


    public void sendVerificationEmail(String to, String verificationUrl) {
        String subject = "Email Verification";
        String text = "Click the link to verify your email: " + verificationUrl;
        sendEmail(to, subject, text);
    }

    public void sendPasswordResetEmail(String to,String subject ,String code) {
        String text = "This is your Reset Code " + code;
        sendEmail(to, subject, text);
    }

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
