package techlog.user.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import techlog.common.exception.BaseException;
import techlog.user.domain.user.repository.UserRepository;
import techlog.user.domain.user.repository.entity.UserEntity;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserEntity createTestUser() {
        return UserEntity.builder()
                .userId("testuser")
                .userPw("password123")
                .name("테스터")
                .build();
    }

    @Test
    @DisplayName("정상 회원가입 시 비밀번호를 암호화하고 저장한다")
    void userSignUp_success() {
        UserEntity user = createTestUser();
        given(userRepository.existsByUserId("testuser")).willReturn(false);
        given(passwordEncoder.encode("password123")).willReturn("encodedPassword");

        userService.userSignUp(user);

        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    @DisplayName("중복 아이디로 가입 시 BaseException을 던진다")
    void userSignUp_duplicateId() {
        UserEntity user = createTestUser();
        given(userRepository.existsByUserId("testuser")).willReturn(true);

        assertThatThrownBy(() -> userService.userSignUp(user))
                .isInstanceOf(BaseException.class)
                .hasMessage("이미 사용 중인 아이디입니다.");

        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(UserEntity.class));
    }
}
