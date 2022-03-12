package app.service.impl;

import app.container.dto.UserDto;
import app.container.dto.request.UserReq;
import app.container.dto.response.ResponseDto;
import app.container.dto.response.StatusDto;
import app.container.entities.UserEntity;
import app.container.enums.OperationStatuses;
import app.container.enums.OperationTypes;
import app.repos.UserRepo;
import app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static app.container.enums.OperationTypes.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo repo;

    @Override
    public ResponseDto addUser(UserReq userReq) {
        var userDto = mapFromRegistrationRequestToDto(userReq);
        var userEntity = getEntity(userDto);
        var status = save(userEntity);
        return getResponse(status, userEntity.getId(), OPERATION_ADD);
    }

    @Override
    public ResponseDto editUser(UUID uuid, UserReq userReq) {
        var user = mapFromRegistrationRequestToDto(userReq);
        var userFromRepo = repo.findCustomerById(uuid);
        if (userFromRepo.isEmpty()) return getResponse(false, uuid, OPERATION_EDIT);
        userFromRepo.get().setName(user.getName());
        userFromRepo.get().setPhone(user.getPhone());
        var status = save(userFromRepo.get());
        return getResponse(status, uuid, OPERATION_EDIT);
    }

    @Override
    public List<UserDto> getUsersList() {
        log.info(String.valueOf(repo.findAllCustomers()));
        return repo.findAllCustomers().stream()
                .map(this::mapFromEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseDto deleteUser(UserReq userReq) {
        boolean status = true;
        var user = repo.findByPhone(userReq.getPhone());
        repo.deleteCustomer(user.get().getPhone());
        return getResponse(status, user.get().getId(), OPERATION_DELETE);
    }

    @Override
    public StatusDto getStatusInfo() {
        repo.findAll();
        return StatusDto.builder()
                .status(HttpStatus.OK)
                .build();
    }

    private ResponseDto getResponse(boolean status, UUID id, OperationTypes operation) {
        return ResponseDto.builder()
                .user_id(id)
                .operation_type(operation)
                .operation_status(getStatus(status)).build();
    }


    private OperationStatuses getStatus(boolean status) {
        return (status) ? OperationStatuses.SUCCESS : OperationStatuses.FAIL;
    }

    private boolean save(UserEntity userEntity) {
        var id = userEntity.getId();
        if (id == null) {
            id = UUID.randomUUID();
        }
        repo.saveCustomer(id, userEntity.getName(), userEntity.getPhone());
        return true;
    }

    private UserEntity getEntity(UserDto user) {
        return UserEntity.builder()
                .name(user.getName())
                .phone(user.getPhone()).build();
    }

    private UserDto mapFromEntityToDto(UserEntity userEntity) {
        return UserDto.builder()
                .name(userEntity.getName())
                .user_id(userEntity.getId())
                .phone(userEntity.getPhone()).build();
    }

    private UserDto mapFromRegistrationRequestToDto(UserReq userReq) {
        return UserDto.builder()
                .name(userReq.getName())
                .phone(userReq.getPhone())
                .build();
    }
}
