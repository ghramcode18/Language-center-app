package Geeks.languagecenterapp.Security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final JWTRequestFilter filter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("api/auth/register", "api/auth/login" ,
                                            "api/placementTest/get/all","api/placementTest/get/lan","api/placementTest/get/num" ,"api/placementTest/book/{placementId}",
                                            "api/service/get/all/with-courses","api/service/get/all" ,"api/course/get/all","api/course/get-course-rate/{id}",
                                            "api/post/get/all","api/post/get/ads","api/post/get/events","api/post/get/ads/asc","api/post/get/ads/desc",
                                            "api/post/get/events/asc","api/post/get/events/desc" ,"api/quiz/get-all-with-questions")
                        .permitAll()
                        .anyRequest()
                        .authenticated());
        return httpSecurity.build();
    }

}