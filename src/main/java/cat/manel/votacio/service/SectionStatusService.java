package cat.manel.votacio.service;

import cat.manel.votacio.domain.SectionStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link SectionStatus}.
 */
public interface SectionStatusService {

    /**
     * Save a sectionStatus.
     *
     * @param sectionStatus the entity to save.
     * @return the persisted entity.
     */
    SectionStatus save(SectionStatus sectionStatus);

    /**
     * Get all the sectionStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SectionStatus> findAll(Pageable pageable);


    /**
     * Get the "id" sectionStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SectionStatus> findOne(Long id);

    /**
     * Delete the "id" sectionStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
