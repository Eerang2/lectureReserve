package techlog.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import techlog.common.exception.BaseException;
import techlog.common.exception.domain.UserException;
import techlog.user.domain.user.repository.UserRepository;
import techlog.user.domain.user.repository.entity.UserEntity;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void userSignUp(UserEntity user) {

        // ID 중복검사
        if (userRepository.existsByUserId(user.getUserId())) {
            throw new BaseException(UserException.DUPLICATE_LOGIN_ID);
        }

        // 비밀번호 암호화
        String encodePw = passwordEncoder.encode(user.getUserPw());

        UserEntity userEntity = UserEntity.of(user, encodePw);

        userRepository.save(userEntity);
    }
}
