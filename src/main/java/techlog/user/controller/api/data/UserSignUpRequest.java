package techlog.user.controller.api.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSignUpRequest {

    @NotBlank(message = "아이디를 입력해주세요.")
    private String userId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    private String userPw;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;
}

