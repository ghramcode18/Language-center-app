package Geeks.languagecenterapp.Controller;
import Geeks.languagecenterapp.DTO.Request.BookRequest;
import Geeks.languagecenterapp.DTO.Request.QuestionQuizRequest;
import Geeks.languagecenterapp.DTO.Request.QuestionRequest;
import Geeks.languagecenterapp.DTO.Request.QuizRequest;
import Geeks.languagecenterapp.DTO.Response.QuizResponse;
import Geeks.languagecenterapp.Model.Enum.UserAccountEnum;
import Geeks.languagecenterapp.Model.UserEntity;
import Geeks.languagecenterapp.Service.QuizService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {
    @Autowired
    private QuizService quizService;
    //add question
    @PostMapping("/add-question")
    public ResponseEntity<?> addQuestion(@AuthenticationPrincipal UserEntity user,@ModelAttribute QuestionRequest body)  {
        Map<String, String> response = new HashMap<>();
        if (user.getAccountType()!= UserAccountEnum.ADMIN){
            // Create a response object with the success message
            response.put("message","You are UnAuthorized");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        return quizService.addQuestion(body);
    }
    //update question
    @PostMapping("/update-question/{id}")
    public ResponseEntity<Object>updateQuestion(@AuthenticationPrincipal UserEntity user,@PathVariable("id") int id , @ModelAttribute QuestionRequest body){
        Map<String, String> response = new HashMap<>();
        if (user.getAccountType()!= UserAccountEnum.ADMIN){
            // Create a response object with the success message
            response.put("message","You are UnAuthorized");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        return quizService.updateQuestion(body, id);
    }
    //delete question
    @DeleteMapping("/delete-question/{id}")
    public ResponseEntity<Object> deleteQuestion(@AuthenticationPrincipal UserEntity user,@PathVariable("id") int id ) {
        Map<String, String> response = new HashMap<>();
        if (user.getAccountType()!= UserAccountEnum.ADMIN){
            // Create a response object with the success message
            response.put("message","You are UnAuthorized");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        return quizService.deleteQuestion(id);
    }
    //add quiz
    @PostMapping("/add-quiz")
    public ResponseEntity<?> addQuiz(@AuthenticationPrincipal UserEntity user,@ModelAttribute QuizRequest body){
        Map<String, String> response = new HashMap<>();
        if (user.getAccountType()!= UserAccountEnum.ADMIN){
            // Create a response object with the success message
            response.put("message","You are UnAuthorized");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        return quizService.addQuiz(body);
    }
    //update quiz
    @PostMapping("/update-quiz/{id}")
    public ResponseEntity<Object>updateQuiz(@AuthenticationPrincipal UserEntity user,@PathVariable("id") int id , @ModelAttribute QuizRequest body){
        Map<String, String> response = new HashMap<>();
        if (user.getAccountType()!= UserAccountEnum.ADMIN){
            // Create a response object with the success message
            response.put("message","You are UnAuthorized");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        return quizService.updateQuiz(body, id);
    }
    //delete quiz
    @DeleteMapping("/delete-quiz/{id}")
    public ResponseEntity<Object> deleteQuiz(@AuthenticationPrincipal UserEntity user,@PathVariable("id") int id ){
        Map<String, String> response = new HashMap<>();
        if (user.getAccountType()!= UserAccountEnum.ADMIN){
            // Create a response object with the success message
            response.put("message","You are UnAuthorized");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        return quizService.deleteQuiz(id);
    }
    //add question to quiz
    @PostMapping("/add-question-quiz/{id}")
    public ResponseEntity<?> addQuizQuestion(@AuthenticationPrincipal UserEntity user,@PathVariable("id") int id ,@ModelAttribute QuestionQuizRequest body)  {
        Map<String, String> response = new HashMap<>();
        if (user.getAccountType()!= UserAccountEnum.ADMIN){
            // Create a response object with the success message
            response.put("message","You are UnAuthorized");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        return quizService.addQuestionToQuiz(body,id);
    }
    //update question to quiz
    @PostMapping("/update-question-quiz/{id}")
    public ResponseEntity<Object>updateQuizQuestion(@AuthenticationPrincipal UserEntity user,@PathVariable("id") int id , @ModelAttribute QuestionQuizRequest body) {
        Map<String, String> response = new HashMap<>();
        if (user.getAccountType()!= UserAccountEnum.ADMIN){
            // Create a response object with the success message
            response.put("message","You are UnAuthorized");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        return quizService.updateQuestionToQuiz(body, id);
    }
    //delete question from quiz
    @DeleteMapping("/delete-question-quiz/{id}")
    public ResponseEntity<Object> deleteQuizQuestion(@AuthenticationPrincipal UserEntity user,@PathVariable("id") int id , @ModelAttribute QuestionQuizRequest body)  {
        Map<String, String> response = new HashMap<>();
        if (user.getAccountType()!= UserAccountEnum.ADMIN){
            // Create a response object with the success message
            response.put("message","You are UnAuthorized");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        return quizService.deleteQuestionFromQuiz(body,id);
    }
    // Get All Quizzes with Questions
    @GetMapping("/get-all-with-questions")
    public ResponseEntity<List<QuizResponse>> getAllQuizzesWithQuestions() {
        List<QuizResponse> quizzes = quizService.getAllQuizzesWithQuestions();
        return ResponseEntity.ok(quizzes);
    }
    //book a placement test
    @PostMapping("/take-quiz/{quizId}")
    public ResponseEntity<Object> bookPlacementTest(@AuthenticationPrincipal UserEntity user,@PathVariable("quizId") int id ) {
        Map<String, String> response = new HashMap<>();
        if (user.getAccountType()!= UserAccountEnum.USER){
            // Create a response object with the success message
            response.put("message","You are UnAuthorized");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        return quizService.takeQuiz(user,id);
    }
}
