package Geeks.languagecenterapp.DTO.Response;

import Geeks.languagecenterapp.Model.ImageEntity;
import Geeks.languagecenterapp.Model.UserEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserProfileResponse {

    UserEntity userInfo;

    List<ImageEntity> userImages;

}
