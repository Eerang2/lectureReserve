package techlog.user.domain.user.repository.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
    USER("user"), ADMIN("admin");
    private final String role;

}