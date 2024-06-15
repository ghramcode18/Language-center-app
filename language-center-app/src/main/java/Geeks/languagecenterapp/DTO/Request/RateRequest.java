package Geeks.languagecenterapp.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RateRequest {
    private int std_id;
    private float rate;
}
