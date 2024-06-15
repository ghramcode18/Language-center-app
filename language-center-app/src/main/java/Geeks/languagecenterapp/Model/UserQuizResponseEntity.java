package Geeks.languagecenterapp.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "userQuizResponse")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserQuizResponseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "userQuizId", nullable = true)
    private UserQuizEntity userQuiz;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "questionId", nullable = true)
    private QuestionEntity question;

    private String userAnswer;

    private boolean isCorrect;
}