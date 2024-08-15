package Geeks.languagecenterapp.DTO.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Setter
@Getter
public class UploadHomeworkRequest {

    private Integer courseId;

    private String description;

    private MultipartFile homeworkFile;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

}