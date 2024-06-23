package Geeks.languagecenterapp.Service;

import Geeks.languagecenterapp.DTO.Request.ServiceRequest;
import Geeks.languagecenterapp.DTO.Response.CourseDayResponse;
import Geeks.languagecenterapp.DTO.Response.CourseResponse;
import Geeks.languagecenterapp.DTO.Response.ServiceWithCourseResponse;
import Geeks.languagecenterapp.Model.CourseDayEntity;
import Geeks.languagecenterapp.Model.CourseEntity;
import Geeks.languagecenterapp.Model.ServiceEntity;
import Geeks.languagecenterapp.Repository.CourseRepository;
import Geeks.languagecenterapp.Repository.ServiceRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceService {
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private CourseRepository courseRepository;

    //Add Services by admin and return ok , return bad request response otherwise
    public ResponseEntity<Object> add(ServiceRequest serviceRequest) throws JsonProcessingException {
        Map <String,String> response = new HashMap<>();

        try {
            ServiceEntity service = new ServiceEntity();
            service.setName(serviceRequest.getName());
            serviceRepository.save(service);

            // Create a response object with the success message
            response.put("message","Service added successfully.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            // Create a response object with the success message
            response.put("message","Something went wrong.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Search for Service by id ...if found -> update info ...else return not found response
    public ResponseEntity<Object> update(ServiceRequest serviceRequest, int id) throws JsonProcessingException {
        Map <String,String> response = new HashMap<>();
        Optional<ServiceEntity> service = serviceRepository.findById(id);
        if (service.isPresent()) {
            try {
                service.get().setName(serviceRequest.getName());
                serviceRepository.save(service.get());

                // Create a response object with the success message
                response.put("message","Service updated successfully.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (Exception e) {
                // Create a response object with the success message
                response.put("message","Something went wrong.");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            // Create a response object with the success message
            response.put("message","Service not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    //Search for Service by id ...if found -> delete info ...else return not found response
    public ResponseEntity<Object> delete(int id) throws JsonProcessingException {
        Map <String,String> response = new HashMap<>();
        Optional<ServiceEntity> service = serviceRepository.findById(id);
        if (service.isPresent()) {
            try {
                serviceRepository.delete(service.get());

                // Create a response object with the success message
                response.put("message","Service deleted successfully.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (Exception e) {
                // Create a response object with the success message
                response.put("message","Something went wrong.");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            // Create a response object with the success message
            response.put("message","Service not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    //get all Services
    public List<ServiceEntity> getAll() {
        return serviceRepository.findAll();
    }
    // Get all Services and their associated Courses
    public List<ServiceWithCourseResponse> getAllWithCourses() {
        List<ServiceEntity> services = serviceRepository.findAll();
        return services.stream()
                .map(this::mapToServiceWithCourseResponse)
                .collect(Collectors.toList());
    }

    private ServiceWithCourseResponse mapToServiceWithCourseResponse(ServiceEntity serviceEntity) {
        List<CourseResponse> courseResponses = serviceEntity.getCourses().stream()
                .map(this::mapToCourseDTO)
                .collect(Collectors.toList());

        return new ServiceWithCourseResponse(serviceEntity.getName(), courseResponses);
    }

    private CourseResponse mapToCourseDTO(CourseEntity courseEntity) {
        CourseResponse dto = new CourseResponse();
        dto.setTitle(courseEntity.getTitle());
        dto.setDescription(courseEntity.getDescription());
        dto.setPrice(courseEntity.getPrice());
        dto.setNumOfHours(courseEntity.getNumOfHours());
        dto.setNumOfSessions(courseEntity.getNumOfSessions());
        dto.setNumOfRoom(courseEntity.getNumOfRoom());
        dto.setStartDate(courseEntity.getStartDate());
        dto.setProgress(courseEntity.getProgress());
        dto.setLevel(courseEntity.getLevel());

        List<CourseDayResponse> courseDayDTOs = courseEntity.getCourseDayList().stream()
                .map(this::mapToCourseDayDTO)
                .collect(Collectors.toList());
        dto.setCourseDayList(courseDayDTOs);
        return dto;
    }

    private CourseDayResponse mapToCourseDayDTO(CourseDayEntity courseDayEntity) {
        CourseDayResponse dto = new CourseDayResponse();
        dto.setId(courseDayEntity.getId());
        dto.setDay(courseDayEntity.getDay().getDay());
        dto.setCourseTime(courseDayEntity.isCourseTime() ? "Morning" : "Evening");
        return dto;
    }
}
