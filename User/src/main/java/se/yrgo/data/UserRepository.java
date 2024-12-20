package se.yrgo.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.yrgo.domain.UserEntity;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
