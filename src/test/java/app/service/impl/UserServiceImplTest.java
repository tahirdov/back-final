package app.service.impl;

import app.container.dto.UserDto;
import app.container.dto.request.UserReq;
import app.container.dto.response.ResponseDto;
import app.container.entities.UserEntity;
import app.container.enums.OperationStatuses;
import app.container.enums.OperationTypes;
import app.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
@ExtendWith({MockitoExtension.class})
public class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl service;
    @Mock
    private UserRepo repo;


    private final UserEntity user = getNewUser();
    private final UUID userId = UUID.randomUUID();
    private final UserReq req = getReq();

    private UserReq getReq() {
        return UserReq.builder()
                .name("Name")
                .phone("0123456").build();
    }


    private UserDto getUserDto() {
        return new UserDto(userId, "Name", "99999999");
    }

    private UserEntity getNewUser() {
        return new UserEntity(userId, "Name", "55555555");
    }

    @Before
    public void setup() {
    }

    @Test
    public void addUserSuccess() {
        ResponseDto responseDto = ResponseDto.builder()
                .operation_status(OperationStatuses.SUCCESS)
                .operation_type(OperationTypes.OPERATION_ADD)
                .build();
        Assert.assertThat(service.addUser(req), is(not(equalTo(null))));
        Assert.assertThat(service.addUser(req), is(equalTo(responseDto)));
    }

    @Test
    public void editUserEmpty() {
        when(repo.findCustomerById(any())).thenReturn(Optional.empty());
        Assert.assertThat(service.editUser(userId, req), is(not(equalTo(null))));

    }

    @Test
    public void editUserSuccess() {
        when(repo.findCustomerById(any())).thenReturn(Optional.of(user));
        Assert.assertThat(service.editUser(userId, req), is(not(equalTo(null))));

    }

    @Test
    public void getUserListSuccess() {
        when(repo.findAllCustomers()).thenReturn(Collections.singletonList(user));
        Assert.assertThat(service.getUsersList(), is(not(equalTo(null))));
    }

    @Test
    public void deleteUser() {
        when(repo.findByPhone(anyString())).thenReturn(Optional.of(user));
        Assert.assertThat(service.deleteUser(req), is(not(equalTo(null))));
    }


    @Test
    public void getStatusInfoTest() {
        when(repo.findAll()).thenReturn(Collections.singletonList(user));
        Assert.assertThat(service.getStatusInfo(), is(not(equalTo(null))));
        Assert.assertThat(service.getStatusInfo().getStatus().value(), is(equalTo(200)));
    }

}