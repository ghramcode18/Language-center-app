package Geeks.languagecenterapp.DTO.Response;

import Geeks.languagecenterapp.Model.CourseImageEntity;
import Geeks.languagecenterapp.Model.Enum.PostEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostResponse {
    private String title;

    private String content;

    private PostEnum type;

    private LocalDateTime createdAt;

    private List<CourseImageEntity> images;
}
