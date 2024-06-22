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
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
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
        try {
            ServiceEntity service = new ServiceEntity();
            service.setName(serviceRequest.getName());
            serviceRepository.save(service);

            // Create a response object with the success message
            String successMessage = "Service added successfully.";
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

    //Search for Service by id ...if found -> update info ...else return not found response
    public ResponseEntity<Object> update(ServiceRequest serviceRequest, int id) throws JsonProcessingException {
        Optional<ServiceEntity> service = serviceRepository.findById(id);
        if (service.isPresent()) {
            try {
                service.get().setName(serviceRequest.getName());
                serviceRepository.save(service.get());

                // Create a response object with the success message
                String successMessage = "Service updated successfully.";
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
            String notFoundMessage = "Service not found.";
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(notFoundMessage);
            return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
        }
    }

    //Search for Service by id ...if found -> delete info ...else return not found response
    public ResponseEntity<Object> delete(int id) throws JsonProcessingException {
        Optional<ServiceEntity> service = serviceRepository.findById(id);
        if (service.isPresent()) {
            try {
                serviceRepository.delete(service.get());

                // Create a response object with the success message
                String successMessage = "Service deleted successfully.";
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
            String notFoundMessage = "Service not found.";
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(notFoundMessage);
            return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
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
