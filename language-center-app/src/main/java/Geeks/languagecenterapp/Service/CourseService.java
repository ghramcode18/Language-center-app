package Geeks.languagecenterapp.Service;

import Geeks.languagecenterapp.DTO.Request.CourseRequest;
import Geeks.languagecenterapp.Model.*;
import Geeks.languagecenterapp.Repository.CourseRepository;
import Geeks.languagecenterapp.Repository.FavoriteRepository;
import Geeks.languagecenterapp.Repository.ServiceRepository;
import Geeks.languagecenterapp.Repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private FavoriteRepository favoriteRepository;


    //Add Course by admin and return ok , return bad request response otherwise
    public ResponseEntity<Object> add(CourseRequest courseRequest) throws JsonProcessingException {
        try {
            CourseEntity course = new CourseEntity();
            Optional<UserEntity> teacher = userRepository.findById(courseRequest.getTeacher_id());
            Optional<ServiceEntity> service=serviceRepository.findById(courseRequest.getService_id());
            if (teacher.isPresent() && service.isPresent()) {
                course.setUser(teacher.get());
                course.setService(service.get());
                course.setTitle(courseRequest.getTitle());
                course.setDescription(courseRequest.getDescription());
                course.setPrice(courseRequest.getPrice());
                course.setNumOfHours(courseRequest.getNumOfHours());
                course.setNumOfSessions(courseRequest.getNumOfSessions());
                course.setNumOfRoom(courseRequest.getNumOfRoom());
                course.setStartDate(courseRequest.getStartDate());
                course.setLevel(courseRequest.getLevel());
                courseRepository.save(course);
            }

            // Create a response object with the success message
            String successMessage = "Course added successfully.";
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(successMessage);
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        } catch (Exception e) {
            // Create a response object with the error message
            String errorMessage = "Something went wrong.";
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(errorMessage);
            return new ResponseEntity<>(jsonResponse, HttpStatus.BAD_REQUEST);
        }
    }

    //Search for Course by id ...if found -> update info ...else return not found response
    public ResponseEntity<Object> update(CourseRequest courseRequest, int id) throws JsonProcessingException {
        Optional<CourseEntity> course = courseRepository.findById(id);
        if (course.isPresent()) {
            try {
                Optional<UserEntity> user = userRepository.findById(courseRequest.getTeacher_id());
                Optional<ServiceEntity> service=serviceRepository.findById(courseRequest.getService_id());
                if (user.isPresent() && service.isPresent()) {
                    course.get().setUser(user.get());
                    course.get().setService(service.get());
                    course.get().setTitle(courseRequest.getTitle());
                    course.get().setDescription(courseRequest.getDescription());
                    course.get().setPrice(courseRequest.getPrice());
                    course.get().setNumOfHours(courseRequest.getNumOfHours());
                    course.get().setNumOfSessions(courseRequest.getNumOfSessions());
                    course.get().setNumOfRoom(courseRequest.getNumOfRoom());
                    course.get().setStartDate(courseRequest.getStartDate());
                    course.get().setLevel(courseRequest.getLevel());
                    courseRepository.save(course.get());
                    // Create a response object with the success message
                    String successMessage = "Course updated successfully.";
                    ObjectMapper objectMapper = new ObjectMapper();
                    String jsonResponse = objectMapper.writeValueAsString(successMessage);
                    return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
                }
                else {
                    // Create a response object with the success message
                    String successMessage = "Some error occurred.";
                    ObjectMapper objectMapper = new ObjectMapper();
                    String jsonResponse = objectMapper.writeValueAsString(successMessage);
                    return new ResponseEntity<>(jsonResponse, HttpStatus.BAD_REQUEST);
                }

            } catch (Exception e) {
                // Create a response object with the error message
                String errorMessage = "Something went wrong.";
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonResponse = objectMapper.writeValueAsString(errorMessage);
                return new ResponseEntity<>(jsonResponse, HttpStatus.BAD_REQUEST);
            }
        } else {
            // Create a response object with the not found message
            String notFoundMessage = "Course not found.";
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(notFoundMessage);
            return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
        }
    }

    //Search for Course by id ...if found -> delete info ...else return not found response
    public ResponseEntity<Object> delete(int id) throws JsonProcessingException {
        Optional<CourseEntity> course = courseRepository.findById(id);
        if (course.isPresent()) {
            try {
                courseRepository.delete(course.get());

                // Create a response object with the success message
                String successMessage = "Course deleted successfully.";
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonResponse = objectMapper.writeValueAsString(successMessage);
                return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
            } catch (Exception e) {
                // Create a response object with the error message
                String errorMessage = "Something went wrong.";
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonResponse = objectMapper.writeValueAsString(errorMessage);
                return new ResponseEntity<>(jsonResponse, HttpStatus.BAD_REQUEST);
            }
        } else {
            // Create a response object with the not found message
            String notFoundMessage = "Course not found.";
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(notFoundMessage);
            return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
        }
    }
    //get all Services
    public List<CourseEntity> getAll() {
        return courseRepository.findAll();
    }


    // Add course to favorite
    public ResponseEntity<Object> addToFavorite(int courseId, UserEntity user) throws JsonProcessingException {
        Optional<CourseEntity> course = courseRepository.findById(courseId);
        if (course.isPresent()) {
            FavoriteEntity favorite = new FavoriteEntity();
            favorite.setUser(user);
            favorite.setCourse(course.get());
            favoriteRepository.save(favorite);

            String successMessage = "Course added to favorites.";
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(successMessage);
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        } else {
            String notFoundMessage = "Course not found.";
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(notFoundMessage);
            return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
        }
    }

    // Remove course from favorite
    public ResponseEntity<Object> deleteFromFavorite(int courseId, UserEntity user) throws JsonProcessingException {
        Optional<CourseEntity> course = courseRepository.findById(courseId);
        if (course.isPresent()) {
            FavoriteEntity favorite = favoriteRepository.findByUserAndCourse(user, course.get());
            if (favorite != null) {
                favoriteRepository.delete(favorite);

                String successMessage = "Course removed from favorites.";
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonResponse = objectMapper.writeValueAsString(successMessage);
                return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
            } else {
                String notFoundMessage = "Favorite not found.";
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonResponse = objectMapper.writeValueAsString(notFoundMessage);
                return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
            }
        } else {
            String notFoundMessage = "Course not found.";
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(notFoundMessage);
            return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
        }
    }


}
