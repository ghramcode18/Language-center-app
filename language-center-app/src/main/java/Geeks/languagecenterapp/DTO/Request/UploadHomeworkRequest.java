package Geeks.languagecenterapp.DTO.Request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class UploadHomeworkRequest {

    Integer courseId;

    String description;

    MultipartFile homeworkFile;

}