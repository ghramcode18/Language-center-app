package Geeks.languagecenterapp.Service;
import Geeks.languagecenterapp.DTO.Request.BookRequest;
import Geeks.languagecenterapp.DTO.Request.PlacementTestRequest;
import Geeks.languagecenterapp.DTO.Request.PostRequest;
import Geeks.languagecenterapp.DTO.Response.ScheduleResponse;
import Geeks.languagecenterapp.Model.BookEntity;
import Geeks.languagecenterapp.Model.Enum.UserAccountEnum;
import Geeks.languagecenterapp.Model.PlacementTestEntity;
import Geeks.languagecenterapp.Model.PostEntity;
import Geeks.languagecenterapp.Model.UserEntity;
import Geeks.languagecenterapp.Repository.BookRepository;
import Geeks.languagecenterapp.Repository.PlacementTestRepository;
import Geeks.languagecenterapp.Repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlacementTestService {
    @Autowired
    private PlacementTestRepository placementTestRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    //Add Placement Test by admin and return ok , return bad request response otherwise
    public ResponseEntity<Object> add(PlacementTestRequest RequestBody) throws JsonProcessingException {
        try {
            PlacementTestEntity placementTest = new PlacementTestEntity();
            placementTest.setLanguage(RequestBody.getLanguage());
            placementTest.setMaxNum(RequestBody.getMaxNum());
            placementTest.setDate(RequestBody.getDate());
            placementTestRepository.save(placementTest);

            // Create a response object with the success message
            String successMessage = "Placement test added successfully.";
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

    //Search for placement test by id ...if found -> update info ...else return not found response
    public ResponseEntity<Object> update(PlacementTestRequest RequestBody, int id) throws JsonProcessingException {
        Optional<PlacementTestEntity> placementTest = placementTestRepository.findById(id);
        if (placementTest.isPresent()) {
            try {
                placementTest.get().setLanguage(RequestBody.getLanguage());
                placementTest.get().setMaxNum(RequestBody.getMaxNum());
                placementTest.get().setDate(RequestBody.getDate());
                placementTestRepository.save(placementTest.get());

                // Create a response object with the success message
                String successMessage = "Placement test updated successfully.";
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
            String notFoundMessage = "Placement test not found.";
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(notFoundMessage);
            return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
        }
    }

    //Search for placement test by id ...if found -> delete info ...else return not found response
    public ResponseEntity<Object> delete(int id) throws JsonProcessingException {
        Optional<PlacementTestEntity> placementTest = placementTestRepository.findById(id);
        if (placementTest.isPresent()) {
            try {
                placementTestRepository.delete(placementTest.get());

                // Create a response object with the success message
                String successMessage = "Placement test deleted successfully.";
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
            String notFoundMessage = "Placement test not found.";
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(notFoundMessage);
            return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
        }
    }

    //get all placement tests
    public List<PlacementTestEntity> getAll() {
        return placementTestRepository.findAll();
    }

    //get all placement tests By Language
    public List<PlacementTestEntity> getAllByLanguage(String language) {
        return placementTestRepository.findByLanguage(language);
    }

    //get all placement tests by Max Number
    public List<PlacementTestEntity> getAllByMaxNum(int maxNum) {
        return placementTestRepository.findByMaxNum(maxNum);
    }

    //Book a placement test
    public ResponseEntity<Object> book(BookRequest RequestBody , int id) throws JsonProcessingException {

        Optional<PlacementTestEntity> placementTest = placementTestRepository.findById(id);

        // Check if the placement test exists
        if (!placementTest.isPresent()) {
            // Create a response object with the success message
            String successMessage = "Placement test not found.";
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(successMessage);
            return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
        }

        // Search for user by phone number
        Optional<UserEntity> user = userRepository.findByPhoneNumber(RequestBody.getPhoneNumber());
        UserEntity userEntity;
        BookEntity book = new BookEntity();

        // If the user is not found, create a new guest user
        if (!user.isPresent()) {
            userEntity = new UserEntity();
            userEntity.setPhoneNumber(RequestBody.getPhoneNumber());
            userEntity.setFirstName(RequestBody.getFirstName());
            userEntity.setLastName(RequestBody.getLastName());
            userEntity.setAccountType(UserAccountEnum.GUEST);
            userRepository.save(userEntity);
        } else {
            userEntity = user.get();
        }

        // Check if the booking already exists for this user and placement test
        Optional<BookEntity> existingBooking = bookRepository.findByUserIdAndPlacementTestId(userEntity.getId(), placementTest.get().getId());
        if (existingBooking.isPresent()) {
            // Create a response object with the success message
            String successMessage = "Book already exists.";
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(successMessage);
            return new ResponseEntity<>(jsonResponse, HttpStatus.CONFLICT);
        }

        // Check the current number of bookings for this placement test
        int currentBookings = bookRepository.countByPlacementTestId(placementTest.get().getId());
        if (currentBookings >= placementTest.get().getMaxNum()) {
            // Create a response object with the success message
            String successMessage = "The maximum number of bookings for this placement test has been reached.";
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(successMessage);
            return new ResponseEntity<>(jsonResponse, HttpStatus.CONFLICT);
        }

        // Create and save the new booking
        book.setUser(userEntity);
        book.setPlacementTest(placementTest.get());
        book.setBookingDate(LocalDateTime.now());
        bookRepository.save(book);

        // Create a response object with the success message
        String successMessage = "Book added successfully.";
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(successMessage);
        return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
    }

    //get all placement tests with the users that books on it
    public List<ScheduleResponse> getBooks() {
        List<PlacementTestEntity> placementTests = placementTestRepository.findAll();

        return placementTests.stream()
                .map(this::mapToScheduleResponse)
                .collect(Collectors.toList());
    }

    private ScheduleResponse mapToScheduleResponse(PlacementTestEntity placementTestEntity) {
        List<BookRequest> bookRequestBodies = bookRepository.findByPlacementTestId(placementTestEntity.getId())
                .stream()
                .map(book -> {
                    BookRequest bookRequest = new BookRequest();
                    bookRequest.setFirstName(book.getUser().getFirstName());
                    bookRequest.setLastName(book.getUser().getLastName());
                    bookRequest.setPhoneNumber(book.getUser().getPhoneNumber());
                    return bookRequest;
                })
                .collect(Collectors.toList());

        return new ScheduleResponse(
                placementTestEntity.getLanguage(),
                placementTestEntity.getMaxNum(),
                placementTestEntity.getDate(),
                bookRequestBodies
        );
    }
}


