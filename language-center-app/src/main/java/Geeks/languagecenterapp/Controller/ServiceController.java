package Geeks.languagecenterapp.Controller;

import Geeks.languagecenterapp.DTO.Request.ServiceRequest;
import Geeks.languagecenterapp.DTO.Response.ServiceWithCourseResponse;
import Geeks.languagecenterapp.Model.Enum.UserAccountEnum;
import Geeks.languagecenterapp.Model.ServiceEntity;
import Geeks.languagecenterapp.Model.UserEntity;
import Geeks.languagecenterapp.Service.ServiceService;
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
@RequestMapping("/api/service")
public class ServiceController {
    @Autowired
    private ServiceService serviceService;

    //Create Service
    @PostMapping("/add")
    public ResponseEntity<Object> addService(@AuthenticationPrincipal UserEntity user , @ModelAttribute ServiceRequest body) throws JsonProcessingException {
        Map<String, String> response = new HashMap<>();
        if (user.getAccountType()!= UserAccountEnum.ADMIN){
            // Create a response object with the success message
            response.put("message","You are UnAuthorized");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        return serviceService.add(body);
    }
    //update Service
    @PostMapping("/update/{id}")
    public ResponseEntity<Object> updateService(@AuthenticationPrincipal UserEntity user ,@PathVariable("id") int id, @ModelAttribute ServiceRequest body) throws JsonProcessingException {
        Map<String, String> response = new HashMap<>();
        if (user.getAccountType()!= UserAccountEnum.ADMIN){
            // Create a response object with the success message
            response.put("message","You are UnAuthorized");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        return serviceService.update(body, id);
    }
    //delete Service
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteService(@AuthenticationPrincipal UserEntity user ,@PathVariable("id") int id) throws JsonProcessingException {
        Map<String, String> response = new HashMap<>();
        if (user.getAccountType()!= UserAccountEnum.ADMIN){
            // Create a response object with the success message
            response.put("message","You are UnAuthorized");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        return serviceService.delete(id);
    }
    //get All Services
    @GetMapping("/get/all")
    public ResponseEntity<List<ServiceEntity>> getAllServices() {
        return ResponseEntity.ok(serviceService.getAll());
    }

    // Get All Services with courses
    @GetMapping("/get/all/with-courses")
    public ResponseEntity<?> getAllServicesAndCourses() {
        List<ServiceWithCourseResponse> services = serviceService.getAllWithCourses();
        return ResponseEntity.ok(services);
    }

}
