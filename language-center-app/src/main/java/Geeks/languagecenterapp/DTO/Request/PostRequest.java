package Geeks.languagecenterapp.DTO.Request;

import Geeks.languagecenterapp.Model.Enum.PostEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostRequest {
    private String title;

    private String content;

    private PostEnum type;

}
