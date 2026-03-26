package com.resiflow.repository;

import com.resiflow.entity.Invitation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    List<Invitation> findAllByResidenceId(Long residenceId);

    Optional<Invitation> findByIdAndResidenceId(Long id, Long residenceId);
}
