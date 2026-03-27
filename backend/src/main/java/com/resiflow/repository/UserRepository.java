package com.resiflow.repository;

import com.resiflow.entity.User;
import com.resiflow.entity.UserRole;
import com.resiflow.entity.UserStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findAllByResidence_Id(Long residenceId);

    List<User> findAllByResidence_IdAndStatus(Long residenceId, UserStatus status);

    List<User> findAllByStatus(UserStatus status);

    List<User> findAllByResidence_IdAndRole(Long residenceId, UserRole role);

    Optional<User> findByIdAndResidence_Id(Long id, Long residenceId);
}
