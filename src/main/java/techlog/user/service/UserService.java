package techlog.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import techlog.user.domain.user.repository.UserRepository;
import techlog.user.domain.user.repository.entity.UserEntity;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserEntity userSignUp(UserEntity user) {

        userRepository.findByUserId(user.getUserId())
                .orElseThrow(RuntimeException::new);

        userRepository.save(user);
        return user;
    }
}
