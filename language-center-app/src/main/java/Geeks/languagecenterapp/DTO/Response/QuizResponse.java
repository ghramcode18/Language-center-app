package Geeks.languagecenterapp.DTO.Response;

import Geeks.languagecenterapp.DTO.Request.QuestionRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuizResponse {
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private List<QuestionRequest> questions;
}
