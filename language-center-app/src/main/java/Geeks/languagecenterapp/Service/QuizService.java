package Geeks.languagecenterapp.Service;

import Geeks.languagecenterapp.DTO.Request.QuestionQuizRequest;
import Geeks.languagecenterapp.DTO.Request.QuestionRequest;
import Geeks.languagecenterapp.DTO.Request.QuizRequest;
import Geeks.languagecenterapp.DTO.Response.*;
import Geeks.languagecenterapp.Model.*;
import Geeks.languagecenterapp.Repository.QuestionRepository;
import Geeks.languagecenterapp.Repository.QuizQuestionRepository;
import Geeks.languagecenterapp.Repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuizService {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizQuestionRepository quizQuestionRepository;

    //Add question by admin and return ok , return bad request response otherwise
    public ResponseEntity<?> addQuestion(QuestionRequest questionRequest) {
        Map <String,String> response = new HashMap<>();

        try {
            QuestionEntity question = new QuestionEntity();
            question.setQuestionText(questionRequest.getQuestion());
            question.setOptions(questionRequest.getOptions());
            question.setCorrectAnswer(questionRequest.getAnswer());
            questionRepository.save(question);

            // Create a response object with the success message
            response.put("message","Question added successfully.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            // Create a response object with the success message
            response.put("message","Something went wrong");
            response.put("error",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Search for Question by id ...if found -> update info ...else return not found response
    public ResponseEntity<Object> updateQuestion(QuestionRequest questionRequest, int id){
        Map <String,String> response = new HashMap<>();
        Optional<QuestionEntity> question = questionRepository.findById(id);
        if (question.isPresent()) {
            try {
                question.get().setQuestionText(questionRequest.getQuestion());
                question.get().setOptions(questionRequest.getOptions());
                question.get().setCorrectAnswer(questionRequest.getAnswer());
                questionRepository.save(question.get());

                // Create a response object with the success message
                response.put("message","Question updated successfully.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (Exception e) {
                // Create a response object with the success message
                response.put("message","Something went wrong.");
                response.put("error",e.getMessage());
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            // Create a response object with the success message
            response.put("message","Question not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    //Search for Question by id ...if found -> delete info ...else return not found response
    public ResponseEntity<Object> deleteQuestion(int id) {
        Map <String,String> response = new HashMap<>();
        Optional<QuestionEntity> question = questionRepository.findById(id);
        if (question.isPresent()) {
            try {
                questionRepository.delete(question.get());

                // Create a response object with the success message
                response.put("message","Question deleted successfully.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (Exception e) {
                // Create a response object with the success message
                response.put("message","Something went wrong.");
                response.put("error",e.getMessage());
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            // Create a response object with the success message
            response.put("message","Question not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    //Add quiz by admin and return ok , return bad request response otherwise
    public ResponseEntity<?> addQuiz(QuizRequest quizRequest) {
        Map <String,String> response = new HashMap<>();

        try {
            QuizEntity quiz = new QuizEntity();
            quiz.setTitle(quizRequest.getTitle());
            quiz.setDescription(quizRequest.getDescription());
            quiz.setCreatedAt(LocalDateTime.now());
            quizRepository.save(quiz);

            // Create a response object with the success message
            response.put("message","Quiz added successfully.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            // Create a response object with the success message
            response.put("message","Something went wrong");
            response.put("error",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Search for Quiz by id ...if found -> update info ...else return not found response
    public ResponseEntity<Object> updateQuiz(QuizRequest quizRequest, int id){
        Map <String,String> response = new HashMap<>();
        Optional<QuizEntity> quiz = quizRepository.findById(id);
        if (quiz.isPresent()) {
            try {
                quiz.get().setTitle(quizRequest.getTitle());
                quiz.get().setDescription(quizRequest.getDescription());
                quiz.get().setCreatedAt(LocalDateTime.now());
                quizRepository.save(quiz.get());

                // Create a response object with the success message
                response.put("message","Quiz updated successfully.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (Exception e) {
                // Create a response object with the success message
                response.put("message","Something went wrong.");
                response.put("error",e.getMessage());
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            // Create a response object with the success message
            response.put("message","Quiz not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    //Search for Quiz by id ...if found -> delete info ...else return not found response
    public ResponseEntity<Object> deleteQuiz(int id) {
        Map <String,String> response = new HashMap<>();
        Optional<QuizEntity> quiz = quizRepository.findById(id);
        if (quiz.isPresent()) {
            try {
                quizRepository.delete(quiz.get());

                // Create a response object with the success message
                response.put("message","Quiz deleted successfully.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (Exception e) {
                // Create a response object with the success message
                response.put("message","Something went wrong.");
                response.put("error",e.getMessage());
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            // Create a response object with the success message
            response.put("message","Quiz not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    //Add Question for A quiz
    public ResponseEntity<Object> addQuestionToQuiz(QuestionQuizRequest body, int id) {
        Map <String,String> response = new HashMap<>();

        Optional<QuizEntity> quiz = quizRepository.findById(id);
        Optional<QuestionEntity> question =questionRepository.findById(body.getQuestion_id());
        if (quiz.isPresent()) {
            QuizQuestionEntity quizQuestion = new QuizQuestionEntity();
            quizQuestion.setQuiz(quiz.get());
            quizQuestion.setQuestion(question.get());

            quizQuestionRepository.save(quizQuestion);

            // Create a response object with the success message
            response.put("message","Question added to The Quiz successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        if(!question.isPresent()){
            // Create a response object with the success message
            response.put("message","Question Not Found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        else {
            // Create a response object with the success message
            response.put("message","Quiz Not Found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    //Update Question for A quiz
    public ResponseEntity<Object> updateQuestionToQuiz(QuestionQuizRequest body, int id)  {
        Map <String,String> response = new HashMap<>();

        Optional<QuizEntity> quiz = quizRepository.findById(id);
        Optional<QuestionEntity> question =questionRepository.findById(body.getQuestion_id());
        Optional<QuizQuestionEntity> quizQuestion = quizQuestionRepository.findByQuizIdAndQuestionId(quiz.get().getId(), body.getQuestion_id());
        if (quiz.isPresent() && question.isPresent()) {
            if (quizQuestion.isPresent()) {
                // Create a response object with the success message
                response.put("message","Question already exists in the Quiz.");
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }
            else {
                QuizQuestionEntity newQuizQuestion = new QuizQuestionEntity();
                newQuizQuestion.setQuiz(quiz.get());
                newQuizQuestion.setQuestion(question.get());
                quizQuestionRepository.save(newQuizQuestion);

                // Create a response object with the success message
                response.put("message","Question added to The Quiz successfully.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

        }
        else {
            // Create a response object with the success message
            response.put("message","Quiz Or Question Not Found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    //delete Question from a quiz
    public ResponseEntity<Object> deleteQuestionFromQuiz(QuestionQuizRequest body, int id) {
        Map <String,String> response = new HashMap<>();

        Optional<QuizQuestionEntity> quizQuestion = quizQuestionRepository.findByQuizIdAndQuestionId(id, body.getQuestion_id());
        if (quizQuestion.isPresent()) {
            quizQuestionRepository.delete(quizQuestion.get());

            // Create a response object with the success message
            response.put("message","Question Deleted from Quiz successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            // Create a response object with the success message
            response.put("message","Quiz Not Found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

    }


    // Get all Quizzes with the Questions
    public List<QuizResponse> getAllQuizzesWithQuestions() {
        List<QuizEntity> quizzes = quizRepository.findAll();
        return quizzes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    // Convert QuizEntity to QuizResponse DTO
    private QuizResponse convertToDTO(QuizEntity quiz) {
        QuizResponse dto = new QuizResponse();
        dto.setTitle(quiz.getTitle());
        dto.setDescription(quiz.getDescription());
        dto.setCreatedAt(quiz.getCreatedAt());

        List<QuizQuestionEntity> quizQuestions = quizQuestionRepository.findByQuizId(quiz.getId());
        List<QuestionRequest> questions = quizQuestions.stream()
                .map(qq -> mapToQuestionResponse(qq.getQuestion()))
                .collect(Collectors.toList());
        dto.setQuestions(questions);

        return dto;
    }
    // Helper method to map QuestionEntity to QuestionResponse
    private QuestionRequest mapToQuestionResponse(QuestionEntity question) {
        QuestionRequest dto = new QuestionRequest();
        dto.setQuestion(question.getQuestionText());
        dto.setOptions(question.getOptions());
        dto.setAnswer(question.getCorrectAnswer());
        return dto;
    }
}
