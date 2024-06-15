package Geeks.languagecenterapp.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "userQuiz")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserQuizEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "userId", nullable = true)
    private UserEntity user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "quizId", nullable = true)
    private QuizEntity quiz;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime assignedAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime completedAt;

}
