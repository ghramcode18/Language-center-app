package Geeks.languagecenterapp.Service;

import Geeks.languagecenterapp.DTO.Request.BookRequest;
import Geeks.languagecenterapp.DTO.Request.QuestionQuizRequest;
import Geeks.languagecenterapp.DTO.Request.QuestionRequest;
import Geeks.languagecenterapp.DTO.Request.QuizRequest;
import Geeks.languagecenterapp.DTO.Response.*;
import Geeks.languagecenterapp.Model.*;
import Geeks.languagecenterapp.Model.Enum.UserAccountEnum;
import Geeks.languagecenterapp.Repository.QuestionRepository;
import Geeks.languagecenterapp.Repository.QuizQuestionRepository;
import Geeks.languagecenterapp.Repository.QuizRepository;
import Geeks.languagecenterapp.Repository.UserQuizRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    @Autowired
    private UserQuizRepository userQuizRepository;

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
    public ResponseEntity<Object> addQuestionToQuiz(QuestionQuizRequest body, int quizId) {
        Map<String, String> response = new HashMap<>();
        Optional<QuizEntity> quizOpt = quizRepository.findById(quizId);

        if (!quizOpt.isPresent()) {
            response.put("message", "Quiz not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        QuizEntity quiz = quizOpt.get();
        for (Integer questionId : body.getQuestion_ids()) {
            Optional<QuestionEntity> questionOpt = questionRepository.findById(questionId);

            if (!questionOpt.isPresent()) {
                response.put("message", "Question ID " + questionId + " not found.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            QuestionEntity question = questionOpt.get();
            Optional<QuizQuestionEntity> existingRelation = quizQuestionRepository.findByQuizIdAndQuestionId(quiz.getId(), questionId);

            if (existingRelation.isPresent()) {
                response.put("message", "Question ID " + questionId + " already exists in the Quiz.");
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }

            QuizQuestionEntity quizQuestion = new QuizQuestionEntity();
            quizQuestion.setQuiz(quiz);
            quizQuestion.setQuestion(question);
            quizQuestionRepository.save(quizQuestion);
        }

        response.put("message", "Questions added to the Quiz successfully.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Update Question for A quiz
    public ResponseEntity<Object> updateQuestionToQuiz(QuestionQuizRequest body, int quizId) {
        Map<String, String> response = new HashMap<>();
        Optional<QuizEntity> quizOpt = quizRepository.findById(quizId);

        if (!quizOpt.isPresent()) {
            response.put("message", "Quiz not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        QuizEntity quiz = quizOpt.get();
        for (Integer questionId : body.getQuestion_ids()) {
            Optional<QuestionEntity> questionOpt = questionRepository.findById(questionId);

            if (!questionOpt.isPresent()) {
                response.put("message", "Question ID " + questionId + " not found.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            QuestionEntity question = questionOpt.get();
            Optional<QuizQuestionEntity> existingRelation = quizQuestionRepository.findByQuizIdAndQuestionId(quiz.getId(), questionId);

            if (existingRelation.isPresent()) {
                response.put("message", "Question ID " + questionId + " already exists in the Quiz.");
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }

            QuizQuestionEntity quizQuestion = new QuizQuestionEntity();
            quizQuestion.setQuiz(quiz);
            quizQuestion.setQuestion(question);
            quizQuestionRepository.save(quizQuestion);
        }

        response.put("message", "Questions updated in the Quiz successfully.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //delete Question from a quiz
    public ResponseEntity<Object> deleteQuestionFromQuiz(QuestionQuizRequest body, int quizId) {
        Map<String, String> response = new HashMap<>();
        Optional<QuizEntity> quizOpt = quizRepository.findById(quizId);

        if (!quizOpt.isPresent()) {
            response.put("message", "Quiz not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        QuizEntity quiz = quizOpt.get();
        for (Integer questionId : body.getQuestion_ids()) {
            Optional<QuizQuestionEntity> quizQuestionOpt = quizQuestionRepository.findByQuizIdAndQuestionId(quiz.getId(), questionId);

            if (!quizQuestionOpt.isPresent()) {
                response.put("message", "Question ID " + questionId + " not found in the Quiz.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            quizQuestionRepository.delete(quizQuestionOpt.get());
        }

        response.put("message", "Questions deleted from the Quiz successfully.");
        return new ResponseEntity<>(response, HttpStatus.OK);
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
        dto.setQuizId(quiz.getId());
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
        dto.setQuestionId(question.getId());
        dto.setQuestion(question.getQuestionText());
        dto.setOptions(question.getOptions());
        dto.setAnswer(question.getCorrectAnswer());
        return dto;
    }

    //Book a placement test
    public ResponseEntity<Object> takeQuiz(UserEntity user , int id) {
        Map<String, String> response = new HashMap<>();

        Optional<QuizEntity> quiz = quizRepository.findById(id);

        // Check if the placement test exists
        if (!quiz.isPresent()) {
            // Create a response object with the success message
            response.put("message","Quiz Not Found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        // Check if the booking already exists for this user and placement test
        Optional<UserQuizEntity> existingBooking = userQuizRepository.findByUserIdAndQuizId(user.getId(), quiz.get().getId());
        if (existingBooking.isPresent()) {
            // Create a response object with the success message
            response.put("message","Quiz already taken.");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        UserQuizEntity userQuiz = new UserQuizEntity();
        userQuiz.setQuiz(quiz.get());
        userQuiz.setUser(user);
        userQuiz.setAssignedAt(LocalDateTime.now());
        userQuizRepository.save(userQuiz);
        // Create a response object with the success message
        response.put("message","Quiz taken Successfully.");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
