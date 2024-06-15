package Geeks.languagecenterapp.Controller;

import Geeks.languagecenterapp.DTO.Request.ServiceRequest;
import Geeks.languagecenterapp.DTO.Response.ServiceWithCourseResponse;
import Geeks.languagecenterapp.Model.ServiceEntity;
import Geeks.languagecenterapp.Service.ServiceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service")
public class ServiceController {
    @Autowired
    private ServiceService serviceService;

    //Create Service
    @PostMapping("/add")
    public ResponseEntity<Object> addService(@RequestBody ServiceRequest body) throws JsonProcessingException {
        return serviceService.add(body);
    }

    //update Service
    @PostMapping("/update/{id}")
    public ResponseEntity<Object> updateService(@PathVariable("id") int id, @RequestBody ServiceRequest body) throws JsonProcessingException {
        return serviceService.update(body, id);
    }

    //delete Service
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteService(@PathVariable("id") int id) throws JsonProcessingException {
        return serviceService.delete(id);
    }

    //get All Services
    @GetMapping("/get/all")
    public ResponseEntity<List<ServiceEntity>> getAllServices() {
        return ResponseEntity.ok(serviceService.getAll());
    }

    // Get All Services with courses
    @GetMapping("/get/all/with-courses")
    public ResponseEntity<List<ServiceWithCourseResponse>> getAllServicesAndCourses() {
        List<ServiceWithCourseResponse> services = serviceService.getAllWithCourses();
        return ResponseEntity.ok(services);
    }

}
