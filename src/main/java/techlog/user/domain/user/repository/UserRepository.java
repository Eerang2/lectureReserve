package techlog.user.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import techlog.user.domain.user.repository.entity.UserEntity;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByUserId(String userId);
}
