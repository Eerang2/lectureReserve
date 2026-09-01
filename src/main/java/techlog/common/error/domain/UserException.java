package techlog.common.error.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import techlog.common.error.ErrorCode;

@Getter
@AllArgsConstructor
public enum UserException implements ErrorCode {

    DUPLICATE_LOGIN_ID(HttpStatus.CONFLICT, "이미 사용 중인 아이디입니다."),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 일치하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
