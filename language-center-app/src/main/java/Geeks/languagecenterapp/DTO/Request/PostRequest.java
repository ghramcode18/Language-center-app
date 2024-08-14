package Geeks.languagecenterapp.DTO.Request;

import Geeks.languagecenterapp.Model.Enum.PostEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostRequest {
    private String title;

    private String content;

    private PostEnum type;

    private String location;

    private String date;

    private MultipartFile cover;

    private List<MultipartFile> images;

}
