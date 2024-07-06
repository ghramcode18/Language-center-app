package Geeks.languagecenterapp.DTO.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
public class UploadMarksRequset {

    Integer courseId;

    LocalDate uploadDate;

    MultipartFile marksFile;
}
