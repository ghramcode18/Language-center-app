package Geeks.languagecenterapp.Controller;
import Geeks.languagecenterapp.DTO.Request.QuestionQuizRequest;
import Geeks.languagecenterapp.DTO.Request.QuestionRequest;
import Geeks.languagecenterapp.DTO.Request.QuizRequest;
import Geeks.languagecenterapp.DTO.Response.QuizResponse;
import Geeks.languagecenterapp.Service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {
    @Autowired
    private QuizService quizService;
    //add question
    @PostMapping("/add-question")
    public ResponseEntity<?> addQuestion(@ModelAttribute QuestionRequest body)  {
        return quizService.addQuestion(body);
    }
    //update question
    @PostMapping("/update-question/{id}")
    public ResponseEntity<Object>updateQuestion(@PathVariable("id") int id , @ModelAttribute QuestionRequest body){
        return quizService.updateQuestion(body, id);
    }
    //delete question
    @DeleteMapping("/delete-question/{id}")
    public ResponseEntity<Object> deleteQuestion(@PathVariable("id") int id ) {
        return quizService.deleteQuestion(id);
    }
    //add quiz
    @PostMapping("/add-quiz")
    public ResponseEntity<?> addQuiz(@ModelAttribute QuizRequest body){
        return quizService.addQuiz(body);
    }
    //update quiz
    @PostMapping("/update-quiz/{id}")
    public ResponseEntity<Object>updateQuiz(@PathVariable("id") int id , @ModelAttribute QuizRequest body){
        return quizService.updateQuiz(body, id);
    }
    //delete quiz
    @DeleteMapping("/delete-quiz/{id}")
    public ResponseEntity<Object> deleteQuiz(@PathVariable("id") int id ){
        return quizService.deleteQuiz(id);
    }
    //add question to quiz
    @PostMapping("/add-question-quiz/{id}")
    public ResponseEntity<?> addQuizQuestion(@PathVariable("id") int id ,@ModelAttribute QuestionQuizRequest body)  {
        return quizService.addQuestionToQuiz(body,id);
    }
    //update question to quiz
    @PostMapping("/update-question-quiz/{id}")
    public ResponseEntity<Object>updateQuizQuestion(@PathVariable("id") int id , @ModelAttribute QuestionQuizRequest body) {
        return quizService.updateQuestionToQuiz(body, id);
    }
    //delete question from quiz
    @DeleteMapping("/delete-question-quiz/{id}")
    public ResponseEntity<Object> deleteQuizQuestion(@PathVariable("id") int id , @ModelAttribute QuestionQuizRequest body)  {
        return quizService.deleteQuestionFromQuiz(body,id);
    }
    // Get All Quizzes with Questions
    @GetMapping("/get-all-with-questions")
    public ResponseEntity<List<QuizResponse>> getAllQuizzesWithQuestions() {
        List<QuizResponse> quizzes = quizService.getAllQuizzesWithQuestions();
        return ResponseEntity.ok(quizzes);
    }
}
