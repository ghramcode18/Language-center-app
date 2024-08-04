package Geeks.languagecenterapp.DTO.Response;

import Geeks.languagecenterapp.DTO.Request.BookRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ScheduleResponse {
    private int id;
    private String language;

    private int maxNum;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    private List<BookRequest> books;
}
