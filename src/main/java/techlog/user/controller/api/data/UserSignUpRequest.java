package techlog.user.controller.api.data;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSignUpRequest {
    private String userId;
    private String userPw;
    private String name;
}

