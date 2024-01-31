package uz.pdp.website.service.user;

import uz.pdp.website.dto.request.AuthDto;
import uz.pdp.website.dto.request.UserRequestDto;
import uz.pdp.website.entity.UserEntity;
import uz.pdp.website.service.BaseService;

import java.util.UUID;

public interface UserService extends BaseService<UserEntity, UserRequestDto> {

    UserEntity login(AuthDto auth);
    void update(String username, String password, UUID id);
    void updateUserInfo(String address, String direction,String password, UUID id);
    UserEntity myInfo(UUID id);

}
