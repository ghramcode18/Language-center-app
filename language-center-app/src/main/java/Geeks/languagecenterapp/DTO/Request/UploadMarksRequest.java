package Geeks.languagecenterapp.DTO.Request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Setter
@Getter
public class UploadMarksRequest {

    private Integer courseId;

    private LocalDate uploadDate;

    private MultipartFile marksFile;
}
