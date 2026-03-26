package com.resiflow.repository;

import com.resiflow.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findAllByResidence_Id(Long residenceId);

    Optional<User> findByIdAndResidence_Id(Long id, Long residenceId);
}
