package app.service;

import app.container.dto.UserDto;
import app.container.dto.request.UserReq;
import app.container.dto.response.ResponseDto;
import app.container.dto.response.StatusDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface UserService {
    ResponseDto addUser(UserReq userReq);

    ResponseDto editUser(UUID uuid, UserReq userReq);

    List<UserDto> getUsersList();

    ResponseDto deleteUser(UserReq userReq);

    StatusDto getStatusInfo();

}
