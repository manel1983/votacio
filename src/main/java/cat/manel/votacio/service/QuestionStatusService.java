package cat.manel.votacio.service;

import cat.manel.votacio.domain.QuestionStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link QuestionStatus}.
 */
public interface QuestionStatusService {

    /**
     * Save a questionStatus.
     *
     * @param questionStatus the entity to save.
     * @return the persisted entity.
     */
    QuestionStatus save(QuestionStatus questionStatus);

    /**
     * Get all the questionStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<QuestionStatus> findAll(Pageable pageable);


    /**
     * Get the "id" questionStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<QuestionStatus> findOne(Long id);

    /**
     * Delete the "id" questionStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
