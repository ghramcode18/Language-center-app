package Geeks.languagecenterapp.DTO.Response;

import Geeks.languagecenterapp.Model.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderCertificateResponse {

    private UserEntity user;

    private boolean isOrderCertificate;

}
