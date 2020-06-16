package cat.manel.votacio.repository;

import cat.manel.votacio.domain.QuestionStatus;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the QuestionStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionStatusRepository extends JpaRepository<QuestionStatus, Long> {
}
