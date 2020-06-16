package cat.manel.votacio.repository;

import cat.manel.votacio.domain.Section;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Section entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
}
