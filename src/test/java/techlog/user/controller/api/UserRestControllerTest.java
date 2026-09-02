package techlog.user.controller.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import techlog.common.exception.BaseException;
import techlog.common.exception.GlobalExceptionHandler;
import techlog.common.exception.domain.UserException;
import techlog.user.domain.user.repository.entity.UserEntity;
import techlog.user.service.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserRestControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private UserRestController userRestController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userRestController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("정상 회원가입 요청 시 200 OK를 반환한다")
    void signup_success() throws Exception {
        doNothing().when(userService).userSignUp(any(UserEntity.class));

        String requestBody = """
                {"userId": "testuser", "userPw": "password123", "name": "테스터"}
                """;

        mockMvc.perform(post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("아이디가 빈 값이면 400을 반환한다")
    void signup_fail_userId_blank() throws Exception {
        String requestBody = """
                {"userId": "", "userPw": "password123", "name": "테스터"}
                """;

        mockMvc.perform(post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("비밀번호가 빈 값이면 400을 반환한다")
    void signup_fail_userPw_blank() throws Exception {
        String requestBody = """
                {"userId": "testuser", "userPw": "", "name": "테스터"}
                """;

        mockMvc.perform(post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("비밀번호가 8자 미만이면 400을 반환한다")
    void signup_fail_userPw_tooShort() throws Exception {
        String requestBody = """
                {"userId": "testuser", "userPw": "short", "name": "테스터"}
                """;

        mockMvc.perform(post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("이름이 빈 값이면 400을 반환한다")
    void signup_fail_name_blank() throws Exception {
        String requestBody = """
                {"userId": "testuser", "userPw": "password123", "name": ""}
                """;

        mockMvc.perform(post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("중복 아이디로 가입 시 409를 반환한다")
    void signup_fail_duplicateUserId() throws Exception {
        doThrow(new BaseException(UserException.DUPLICATE_LOGIN_ID))
                .when(userService).userSignUp(any(UserEntity.class));

        String requestBody = """
                {"userId": "existinguser", "userPw": "password123", "name": "테스터"}
                """;

        mockMvc.perform(post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("이미 사용 중인 아이디입니다."));
    }
}
