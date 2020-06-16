package cat.manel.votacio.repository;

import cat.manel.votacio.domain.SectionStatus;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SectionStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SectionStatusRepository extends JpaRepository<SectionStatus, Long> {
}
