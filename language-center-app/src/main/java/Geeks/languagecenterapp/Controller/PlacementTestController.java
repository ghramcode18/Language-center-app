package Geeks.languagecenterapp.Controller;
import Geeks.languagecenterapp.DTO.Request.BookRequest;
import Geeks.languagecenterapp.DTO.Request.PlacementTestRequest;
import Geeks.languagecenterapp.DTO.Response.ScheduleResponse;
import Geeks.languagecenterapp.Model.Enum.UserAccountEnum;
import Geeks.languagecenterapp.Model.PlacementTestEntity;
import Geeks.languagecenterapp.Model.UserEntity;
import Geeks.languagecenterapp.Repository.UserRepository;
import Geeks.languagecenterapp.Service.PlacementTestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/placementTest")
public class PlacementTestController {
    @Autowired
    private PlacementTestService placementTestService;


    //Create placement test
    @PostMapping("/add")
    public ResponseEntity<Object> addPlacementTest(@AuthenticationPrincipal UserEntity user ,@ModelAttribute PlacementTestRequest body) throws JsonProcessingException {
        Map<String, String> response = new HashMap<>();
        if (user.getAccountType()!= UserAccountEnum.ADMIN){
            // Create a response object with the success message
            response.put("message","You are UnAuthorized");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        return placementTestService.add(body);
    }
    //update placement test
    @PostMapping("/update/{id}")
    public ResponseEntity<Object> updatePlacementTest(@AuthenticationPrincipal UserEntity user ,@PathVariable("id") int id , @ModelAttribute PlacementTestRequest body) throws JsonProcessingException {
        Map<String, String> response = new HashMap<>();
        if (user.getAccountType()!= UserAccountEnum.ADMIN){
            // Create a response object with the success message
            response.put("message","You are UnAuthorized");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        return placementTestService.update(body, id);
    }
    //delete placement test
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deletePlacementTest(@AuthenticationPrincipal UserEntity user ,@PathVariable("id") int id ) throws JsonProcessingException {
        Map<String, String> response = new HashMap<>();
        if (user.getAccountType()!= UserAccountEnum.ADMIN){
            // Create a response object with the success message
            response.put("message","You are UnAuthorized");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        return placementTestService.delete(id);
    }
    //get All placement test
    @GetMapping("/get/all")
    public ResponseEntity<List<PlacementTestEntity>> getAllPlacementTest(){
        return ResponseEntity.ok(placementTestService.getAll());
    }
    //get All placement test filtered by language
    @GetMapping("/get/lan")
    public ResponseEntity<List<PlacementTestEntity>> getAllPlacementTestByLanguage(@RequestParam String lan){
        return ResponseEntity.ok(placementTestService.getAllByLanguage(lan));
    }
    //get All placement test filtered by max num
    @GetMapping("/get/num")
    public ResponseEntity<List<PlacementTestEntity>> getAllPlacementTestByMaxNum(@RequestParam int num){
        return ResponseEntity.ok(placementTestService.getAllByMaxNum(num));
    }
    //book a placement test
    @PostMapping("/book/{placementId}")
    public ResponseEntity<Object> bookPlacementTest(@PathVariable("placementId") int id , @ModelAttribute BookRequest body) throws JsonProcessingException {
        return placementTestService.book(body,id);
    }
    //return All placement test and all the books
    @GetMapping("/get/schedule")
    public ResponseEntity<?> getAllPlacementTestsWithUsers(@AuthenticationPrincipal UserEntity user ){
        Map<String, String> response = new HashMap<>();
        if (user.getAccountType()!= UserAccountEnum.ADMIN){
            // Create a response object with the success message
            response.put("message","You are UnAuthorized");
            return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
        }
        List<ScheduleResponse> result = placementTestService.getBooks();
        return ResponseEntity.ok(result);
    }



}

