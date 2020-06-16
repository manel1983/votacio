package cat.manel.votacio.repository;

import cat.manel.votacio.domain.AnswerStatus;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AnswerStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnswerStatusRepository extends JpaRepository<AnswerStatus, Long> {
}
