package techlog.user.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import techlog.user.controller.api.data.UserSignUpRequest;
import techlog.user.domain.user.repository.entity.UserEntity;
import techlog.user.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @PostMapping("/api/signup")
    public ResponseEntity<?> signup(@RequestBody UserSignUpRequest userReq) {
        userService.userSignUp(UserEntity.from(userReq));
        return ResponseEntity.ok("201"); // 회원가입 성공
    }
}
