package app.controller;

import app.container.dto.UserDto;
import app.container.dto.request.UserReq;
import app.container.dto.response.ResponseDto;
import app.container.dto.response.StatusDto;
import app.container.enums.OperationStatuses;
import app.container.enums.OperationTypes;
import app.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        value = UserController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE)
)
public class UserControllerTest {
    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private final UserReq req = getReq();
    private final UserDto userDto = getUserDto();

    private UserDto getUserDto() {
        return UserDto.builder()
                .user_id(UUID.randomUUID())
                .name("Name")
                .phone("96352417").build();
    }

    private UserReq getReq() {
        return UserReq.builder()
                .name("Name")
                .phone("77755544").build();
    }

    @SneakyThrows
    @Test
    public void addUserSuccess() {
        ResponseDto responseDto = ResponseDto.builder()
                .operation_status(OperationStatuses.SUCCESS)
                .operation_type(OperationTypes.OPERATION_ADD)
                .build();
        UserReq userRequestData = UserReq.builder()
                .name("Name")
                .phone("1234567")
                .build();
        when(userService.addUser(any())).thenReturn(responseDto);
        final String contentAsString = mockMvc.perform(post("/user/add")
                        .content(objectMapper.writeValueAsString(userRequestData))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertThat(contentAsString, is(not(equalTo(null))));
    }

    @SneakyThrows
    @Test
    public void editUserSuccess() {
        ResponseDto responseDto = ResponseDto.builder()
                .operation_status(OperationStatuses.SUCCESS)
                .operation_type(OperationTypes.OPERATION_EDIT)
                .build();
        UserReq userRequestData = UserReq.builder()
                .name("Name")
                .phone("1234567")
                .build();
        when(userService.editUser(any(), any())).thenReturn(responseDto);
        final String contentAsString = mockMvc.perform(
                        get("/user/edit/{uuid}", UUID.randomUUID())
                                .content(objectMapper.writeValueAsString(userRequestData))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertThat(contentAsString, is(not(equalTo(null))));
    }

    @SneakyThrows
    @Test
    public void getUserListSuccess() {
        when(userService.getUsersList()).thenReturn(Collections.singletonList(userDto));
        final String contentAsString = mockMvc.perform(
                        get("/user/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertThat(contentAsString, is(not(equalTo(null))));
    }

    @Test
    public void deleteUser() throws Exception {
        ResponseDto responseDto = ResponseDto.builder()
                .operation_status(OperationStatuses.SUCCESS)
                .operation_type(OperationTypes.OPERATION_DELETE)
                .build();
        when(userService.deleteUser(any())).thenReturn(responseDto);
        final String contentAsString = mockMvc.perform(
                        get("/user/delete")
                                .content(objectMapper.writeValueAsString(req))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertThat(contentAsString, is(not(equalTo(null))));
    }


    @Test
    public void getStatusInfoTest() throws Exception {
        ResponseDto responseDto = ResponseDto.builder()
                .operation_status(OperationStatuses.SUCCESS)
                .operation_type(OperationTypes.OPERATION_DELETE)
                .build();
        when(userService.getStatusInfo()).thenReturn(StatusDto.builder().status(HttpStatus.OK).build());
        final String contentAsString = mockMvc.perform(
                        get("/status"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertThat(contentAsString, is(not(equalTo(null))));
    }


}
