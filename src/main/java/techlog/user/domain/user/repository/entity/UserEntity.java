package techlog.user.domain.user.repository.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import techlog.user.controller.api.data.UserSignUpRequest;
import techlog.user.domain.user.repository.entity.enums.UserRole;

@Entity
@Table(name = "USER")

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_key")
    private Long id;

    @Column(unique = true)
    private String userId;

    @Column(unique = true)
    private String userPw;

    private String name;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public static UserEntity from(UserSignUpRequest req) {
        return UserEntity.builder()
                .userId(req.getUserId())
                .userPw(req.getUserPw())
                .name(req.getName())
                .build();
    }

    public static UserEntity of(UserEntity user, String encodePw) {
        return UserEntity.builder()
                .userId(user.getUserId())
                .userPw(encodePw)
                .name(user.getName())
                .role(UserRole.USER)
                .build();
    }
}
